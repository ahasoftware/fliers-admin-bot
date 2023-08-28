package kz.aha.bot.data.common;

/**
 * Model to generate keyboard
 *
 * @author Akhmet.Sulemenov
 * created 28.06.2023
 */
public class KeyboardButton {

    private String text;
    private String callbackData;

    public KeyboardButton setText(String text) {
        this.text = text;
        return this;
    }

    public String getText() {
        return text;
    }

    public KeyboardButton setCallbackData(String callbackData) {
        this.callbackData = callbackData;
        return this;
    }

    public String getCallbackData() {
        return callbackData;
    }

}
