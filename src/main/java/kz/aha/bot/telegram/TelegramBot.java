package kz.aha.bot.telegram;

import kz.aha.bot.data.bom.entity.User;
import kz.aha.bot.data.bom.service.*;
import kz.aha.bot.data.bom.service.impl.DefaultDictService;
import kz.aha.bot.service.LocaleMessageService;
import kz.aha.bot.telegram.helper.TelegramHelper;
import kz.aha.bot.util.AppUtils;
import kz.aha.bot.util.enums.LanguageMode;
import kz.aha.bot.util.record.InlineButton;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.*;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

import static kz.aha.bot.util.AppConstants.*;
import static kz.aha.bot.util.enums.LanguageMode.*;

/**
 * Класс TelegramBot
 *
 * @author Akhmet Sulemenov
 * created 28.06.2023
 */
@Slf4j
@Getter
@Component
@RequiredArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {

    private final SendMessage response = new SendMessage();
    private final LocaleMessageService langService;
    private final UserService userService;
    private final TelegramHelper helper;

    private final DefaultDictService defaultDictService;

    private String preMes;
    private boolean check = false;
    @Value("${telegram-bot.name}")
    String botUsername;
    @Value("${telegram-bot.token}")
    String botToken;
    @Value("${telegram-bot.url}")
    String botUrl;
    @Value("${telegram-bot.channelId}")
    String channelId;

    /**
     * Этот метод вызывается при получении обновлений через метод GetUpdates
     *
     * @param request from telegram bot user
     */
    @SneakyThrows
    @Override
    public void onUpdateReceived(Update request) {
        var user = this.userService.findUserByTelegramRequest(request);

        if (user.getLanguageMode() != null) {
            langService.setLang(user.getLanguageMode());
        }

        response.setReplyMarkup(helper.createReplyKeyboardMarkup(true, true, false, false,
                    List.of("reply.sharePhoneNumber"), List.of("reply.help"),List.of("reply.dictCompanies")));

        if (request.hasCallbackQuery()) {
            CallbackQuery callbackQuery = request.getCallbackQuery();
            log.info("New callbackQuery from User: {}, userId: {}, with data: {}", callbackQuery.getFrom().getFirstName(),
                    callbackQuery.getFrom().getId(), callbackQuery.getData());
            response.setChatId(callbackQuery.getMessage().getChatId());
            processCallbackQuery(callbackQuery, user);
            return;
        }

        if (!request.hasMessage()) {
            return;
        }

        response.setChatId(request.getMessage().getChatId());
        response.enableMarkdown(true);

        if (request.getMessage().hasContact()) {
            setPhone(request);
            return;
        }
        if(check){
            preMes = request.getMessage().getText();
            check = false;
        }
        if (langService.getMessage("reply.dictCompanies").equals(request.getMessage().getText())){
            showDictCompanies(user);
            check = true;
        }

        String curMes = request.getMessage().getText();
        System.out.println(preMes);
        System.out.println(curMes);
        conditionInput(request, user);
    }

    /**
     * Метод обрабатывает, что вводит пользователь в боте
     */
    @SneakyThrows
    private void conditionInput(@NotNull Update update, User user) {
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return;
        }
        var command = update.getMessage().getText();
        var previousMessage = user.getPreviousMessage();
        
        log.info("chatId {}, previousMessage {} = {}", response.getChatId(), previousMessage, response.getText());
        log.info("new command[text: {}, username: {}, from: {}, language: {}, previous bot message: {}]",
                command, user.getTFirstName(), user.getUserId(), user.getLanguageMode(), previousMessage);

        var chatId = update.getMessage().getChatId();

        if ("/start".equals(command) || Objects.isNull(user.getLanguageMode())) {
            send(helper.sendSticker(chatId, GREETING_STICKER));
            response.setReplyMarkup(helper.languageButtons());
            send(response, user, MessageFormat.format(langService.getMessage("reply.hello"), user.getTFirstName() + HELLO_STICKER));
        } else if (langService.getMessage("reply.phoneNotConfirmed").equals(previousMessage)) {
            setPhone(command);
        }
    }
    private void showDictCompanies(User user){
        response.setReplyMarkup(helper.createInlineKeyboardMarkup(defaultDictService.getDictCompanies(langService.getLang()),false));
        send(response, user, langService.getMessage("reply.chooseDictCompanies"));
    }
    /**
     * Выбор языка интерфейса
     * @param lang language mode to update
     */
    @SneakyThrows
    private void chooseLang(LanguageMode lang, User user) {
        langService.setLang(lang);
        user.setLanguageMode(lang);
        userService.update(user);
    }

    private void setPhone(Update request) {
        var phone = AppUtils.checkFormatPhone(request.getMessage().getContact().getPhoneNumber());
        var user = userService.findUserByTelegramUserId(request.getMessage().getChatId());
        if (user.isPresent()) {
            var userToSave = user.get();
            userToSave.setPhoneNumber(phone);
            userService.update(userToSave);
        }
        send(response, user.get(), langService.getMessage("reply.phoneConfirmed"));
    }

    /**
     * NOT CONFIRMED
     * @param phoneNumber
     */
    private void setPhone(String phoneNumber) {
        var chatId = Long.parseLong(response.getChatId());
        var phone = AppUtils.checkFormatPhone(phoneNumber);
        var user = userService.findUserByTelegramUserId(chatId);
        if (user.isPresent()) {
            var userToSave = user.get();
            userToSave.setPhoneNumber(phone);
            userService.update(userToSave);
        }
        send(response, user.get(), langService.getMessage("reply.phoneConfirmed"));
    }

    private void processCallbackQuery(CallbackQuery buttonQuery, User user) {
        final long chatId = buttonQuery.getMessage().getChatId();
        final String param = buttonQuery.getData();
        response.setChatId(chatId);

        // удалям предыдущие сообщения
        deleteMessage(String.valueOf(chatId), buttonQuery.getMessage().getMessageId());

        log.info("current callback query [{}]", param);

        if (param.contains("button_language_mode")) {
            LanguageMode lang;
            switch (param) {
                case "button_language_mode_ru" -> lang = RUSSIAN;
                case "button_language_mode_en" -> lang = ENGLISH;
                default -> lang = KAZAKH;
            }
            chooseLang(lang, user);
            sendPolicyInfo(chatId, user);
        }

    }

    /**
     * Отправить информацию о политике
     * @param chatId
     * @param user
     */
    private void sendPolicyInfo(Long chatId, User user) {
        send(
                SendMessage.builder().chatId(chatId).replyMarkup(helper.createReplyKeyboardMarkup(
                                true,
                                true, user.getPhoneNumber() == null, false,
                                List.of("reply.sharePhoneNumber"), List.of("reply.help"), List.of("reply.dictCompanies"),
                                getReplyListAdmin(user, List.of("reply.reports"))))
                        .text(langService.getMessage("reply.policy"))
                        .build(), user, langService.getMessage("reply.policy")
        );
    }

    private List getReplyListAdmin(User user, List<String> commands) {
        return (!userService.isUser(user) ? commands : List.of());
    }

    /**
     * Очистить кнопки
     */
    private void clearInlineMessageButtons() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        inlineKeyboardMarkup.setKeyboard(rowList);
        response.setReplyMarkup(inlineKeyboardMarkup);
    }

    /**
     * Predicate который определяет имеет ли update event какой нибудь файл
     */
    private final Predicate<Update> hasFile = update -> update.hasMessage() && (update.getMessage().hasDocument() || update.getMessage().hasVideo() || update.getMessage().hasPhoto());

    private void send(final SendMessage message, User user, String command) {
        try {
            message.setText(command);
            message.setParseMode("html");
            execute(message);
            // Update previous bot message
            user.setPreviousMessage(command);
            userService.update(user);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    private void send(final SendPhoto message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    private void send(final SendMediaGroup message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    private void send(final SendSticker message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void send(final SendDocument message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * Удаляем сообщение
     */
    private void deleteMessage(String chatId, Integer messageId) {
        DeleteMessage deleteMessage = new DeleteMessage(chatId, messageId);
        try {
            TimeUnit.MICROSECONDS.sleep(300);
            execute(deleteMessage);
        } catch (TelegramApiException | InterruptedException tae) {
            throw new RuntimeException(tae);
        }
    }
}