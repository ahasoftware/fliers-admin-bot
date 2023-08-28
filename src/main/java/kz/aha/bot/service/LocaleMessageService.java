package kz.aha.bot.service;

import kz.aha.bot.util.enums.LanguageMode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * Работает с файлом шаблоном "ответных сообщений" messages.properties
 *
 * @author Akhmet.Sulemenov
 */
@Slf4j
@Service
public class LocaleMessageService {
    private Locale locale;
    private final MessageSource messageSource;

    public LocaleMessageService(
            @Value("${localeTag}") String localeTag,
            MessageSource messageSource
    ) {
        this.messageSource = messageSource;
        this.locale = Locale.forLanguageTag(localeTag);
    }

    public String getMessage(String message, Object... args) {
        //log.info(this.locale.toLanguageTag());
        return messageSource.getMessage(message, args, locale);
    }

    // command cur user lang mode

    public String getMessageLocale(String message, // reply.command_name
                                   LanguageMode languageMode, // language
                                   Object... args // some args
    ) {
        this.locale = Locale.forLanguageTag(languageMode.getName());
        return messageSource.getMessage(message, args, locale);
    }

    public void setLang(LanguageMode lang) {
        this.locale = Locale.forLanguageTag(lang.getName());
        //log.info(this.locale.toLanguageTag());
    }

    private String getLang() {
        return this.locale.toLanguageTag();
    }
}
