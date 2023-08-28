package kz.aha.bot.util.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter
public enum LanguageMode {
    KAZAKH("kz",  "\uD83C\uDDF0\uD83C\uDDFFҚазақ"),
    RUSSIAN("ru", "\ud83c\uddf8\ud83c\uddeeРусский"),
    ENGLISH("en", "\ud83c\uddfa\ud83c\uddf8English");

    private final String name;
    private final String nativeName;

}
