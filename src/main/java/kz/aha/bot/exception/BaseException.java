package kz.aha.bot.exception;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Akhmet.Sulemenov
 * created 28.06.2023
 */
@Slf4j
public class BaseException extends RuntimeException{

    public BaseException(String msg, Throwable t) {
        super(msg, t);
        log.error(msg, t);
    }

    public BaseException(String msg) {
        super(msg);
        log.error(msg);
    }

}
