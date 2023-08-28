package kz.aha.bot.telegram.helper;

import kz.aha.bot.util.record.InlineButton;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.util.List;

/**
 * @author Akhmet.Sulemenov
 * created 18.07.23
 * pattern Mediator
 * Класс с которого все helper классы
 */
@Component
@RequiredArgsConstructor
public class TelegramHelper {

    private final KeyboardHelper keyboardHelper;

    /**
     * Cоздать разметку клавиатуры для ответа
     * @param fromBundleResource - из ресурса пакета
     * @param oneTimeUse - скрываем после использования
     * @param phoneRequired - телефон обязателен
     * @param commands
     * @return
     */
    @SafeVarargs
    public final ReplyKeyboardMarkup createReplyKeyboardMarkup(
            boolean fromBundleResource,
            boolean oneTimeUse,
            boolean phoneRequired,
            boolean locationRequired,
            List<String>... commands
    ) {
        return keyboardHelper.createReplyKeyboardMarkup(fromBundleResource, oneTimeUse, phoneRequired, locationRequired, commands);
    }

    public final InlineKeyboardMarkup createInlineKeyboardMarkup(
            List<List<InlineButton>> commands,
            Boolean fromBundleResource
    ) {
        return keyboardHelper.createInlineKeyboardMarkup(commands, fromBundleResource);
    }

    public SendSticker sendSticker(long chatId, String fileId) {
        return SendSticker.builder()
                .chatId(chatId)
                .sticker(new InputFile(fileId))
                .build();
    }

    public InlineKeyboardMarkup languageButtons() {
        return keyboardHelper.languageButtons();
    }
}
