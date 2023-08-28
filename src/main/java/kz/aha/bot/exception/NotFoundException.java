package kz.aha.bot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Akhmet.Sulemenov
 * created 28.06.2023
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends BaseException {

    private final static String MESSAGE = "Not Found";

    public NotFoundException(Throwable t) {
        super(MESSAGE, t);
    }

    public NotFoundException() {
        super(MESSAGE);
    }
}
