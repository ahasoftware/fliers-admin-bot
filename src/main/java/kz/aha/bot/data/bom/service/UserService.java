package kz.aha.bot.data.bom.service;

import kz.aha.bot.data.bom.entity.User;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Optional;

/**
 * @author Akhmet.Sulemenov
 * created 28.06.2023
 */
@Service
public interface UserService {

    User getTelegramUser(Long id);

    List<User> getAllList();

    void createRole(User User);

    User create(User User);

    User update(User User);

    void delete(Long id);

    boolean isUser(User user);

    User createFromTelegramUpdate(Update update);

    Optional<User> findUserByTelegramUserId(Long id);

    User findUserByTelegramRequest(Update request);

}
