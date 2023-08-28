package kz.aha.bot.service;

import kz.aha.bot.exception.NotFoundException;

import java.util.List;

/**
 * Класс BaseService реализует базовые методы сервисов проекта
 *
 * @author Akhmet.Sulemenov
 * created 29.06.2023
 */
public class BaseService {

    /**
     * Обёртка результата
     *
     * @param result результат
     * @return результат
     * @throws NotFoundException если результат null
     */
    public <T> T wrapResult(T result) {
        if(result == null)
            throw new NotFoundException();
        return result;
    }

    /**
     * Обёртка результата
     *
     * @param result результат
     * @return результат
     * @throws NotFoundException если результат null или пустой
     */
    public <T> List<T> wrapResults(List<T> result) {
        if(result == null || result.size() == 0)
            throw new NotFoundException();
        return result;
    }

}
