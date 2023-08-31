package kz.aha.bot.telegram.helper;

import kz.aha.bot.service.LocaleMessageService;
import kz.aha.bot.util.record.InlineButton;
import kz.aha.bot.util.enums.LanguageMode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.*;

/**
 * @author Akhmet.Sulemenov
 * created 18.07.23
 * Класс который помогает боту реaлизовать команды с клавиратурой
 */
@Component
@RequiredArgsConstructor
public class KeyboardHelper {

    private final LocaleMessageService langService;

    /**
     * @param commands           each list => row of buttons with command
     * @param fromBundleResource if true text will command from lang service
     */
    @SafeVarargs
    public final ReplyKeyboardMarkup createReplyKeyboardMarkup(
            boolean fromBundleResource,
            boolean oneTimeUse,
            boolean phoneRequired,
            boolean locationRequired,
            List<String>... commands // we can change to Map[command, requestContact]
    ) {
        ReplyKeyboardMarkup markup = ReplyKeyboardMarkup.builder()
                .selective(true)
                .oneTimeKeyboard(oneTimeUse) //скрываем после использования
                .resizeKeyboard(true) //подгоняем размер
                .build();

        // Создаем список строк клавиатуры
        List<KeyboardRow> keyboard = new ArrayList<>();

        /*
        создание клавиш с коммандами
        */
        Arrays.stream(commands)
                .forEach(listOfCommand -> {
                            KeyboardRow row = new KeyboardRow();
                            listOfCommand.forEach(
                                    command -> {
                                        if(command.equals("reply.sharePhoneNumber")){
                                            if(phoneRequired) {
                                                row.add(KeyboardButton.builder()
                                                        .text(fromBundleResource ? langService.getMessage(command) : command)
                                                        .requestContact(true)
                                                        .build());
                                            }
                                        }else{
                                            row.add(KeyboardButton.builder()
                                                    .text(fromBundleResource ? langService.getMessage(command) : command)
                                                    .requestLocation(locationRequired)
                                                    .build());
                                        }
                                    }
                            );
                            keyboard.add(row);
                        }
                );

        markup.setKeyboard(keyboard);

        return markup;
    }

    public InlineKeyboardMarkup languageButtons() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        var keyboard = new ArrayList<List<InlineKeyboardButton>>();

        Iterator<LanguageMode> languageIterator = Arrays.stream(LanguageMode.values()).iterator();
        for (int i = 0; i < LanguageMode.values().length; i++) {
            List<InlineKeyboardButton> rowInline = new ArrayList<>();
            for (int j = 0; j < 1; j++) {
                var lang = languageIterator.next();
                rowInline.add(InlineKeyboardButton.builder()
                        .text(lang.getNativeName())
                        .callbackData("button_language_mode_".concat(lang.getName()))
                        .build()
                );
            }
            keyboard.add(rowInline);
        }
        inlineKeyboardMarkup.setKeyboard(keyboard);
        return inlineKeyboardMarkup;
    }

    public final InlineKeyboardMarkup createInlineKeyboardMarkup(
            List<List<InlineButton>> commands
            , Boolean fromBundleResource
    ) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        var keyboard = new ArrayList<List<InlineKeyboardButton>>();
        commands.forEach(innerCommands -> {
            List<InlineKeyboardButton> row = new ArrayList<>();
            innerCommands.stream().map(command ->
                    InlineKeyboardButton.builder()
                            .callbackData(command.callbackData())
                            .text(fromBundleResource ? langService.getMessage(command.name()) : command.name())
                            .build()
            ).forEach(row::add);
            keyboard.add(row);
        });

        inlineKeyboardMarkup.setKeyboard(keyboard);

        return inlineKeyboardMarkup;
    }
}
