package kz.aha.bot.data.bom.service.impl;

import kz.aha.bot.data.bom.entity.User;
import kz.aha.bot.data.bom.entity.UserRole;
import kz.aha.bot.data.bom.repository.UserRepository;
import kz.aha.bot.data.bom.repository.UserRoleRepository;
import kz.aha.bot.data.bom.service.UserService;
import kz.aha.bot.util.enums.LanguageMode;
import kz.aha.bot.util.enums.RoleType;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Akhmet.Sulemenov
 * created 28.06.2023
 */
@Service
@Log4j2
@RequiredArgsConstructor
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    @Override
    public User getTelegramUser(Long id) {
        return userRepository.findByUserId(id).get();
    }

    @Override
    public List<User> getAllList() {
        return userRepository.findAll();
    }


    @Override
    public void createRole(User user) {
        UserRole userRole = new UserRole(RoleType.USER);
        userRole.setRole(RoleType.USER);
        userRole.setUser(user);
        userRoleRepository.save(userRole);
    }

    @Override
    public User create(User user) {
        Optional<User> optionalUser = userRepository.findByUserId(user.getUserId());
        optionalUser.ifPresent(u -> user.setId(u.getId()));
        return this.userRepository.save(user);
    }

    @Override
    public User update(User user) {
        return this.create(user);
    }

    @Override
    public void delete(Long id) {
        this.userRepository.deleteById(id);
    }

    /**
     * Доступ пользователям
     * @param entity
     * @return
     */
    @Override
    public boolean isUser(User entity) {
        List<UserRole> roles = entity.getUserRoles();
        for (UserRole dto : roles) {
            if (RoleType.ADMIN.equals(dto.getRole())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public User createFromTelegramUpdate(Update update) {
        var telegramUser = update.getMessage().getFrom();
        var user = userRepository.save(User.builder()
                .tUserName(telegramUser.getUserName())
                .tFirstName(telegramUser.getFirstName())
                .userRoles(new ArrayList<>())
                .userId(telegramUser.getId())
                .languageMode(LanguageMode.KAZAKH)
                .build());

        var role = userRoleRepository.save(UserRole.builder().role(RoleType.USER).user(user).build());
        user.getUserRoles().add(role);
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findUserByTelegramUserId(Long id) {
        return userRepository.findUserByUserId(id);
    }

    @Override
    public User findUserByTelegramRequest(Update request) {
        var id = 0L;
        if (request.hasCallbackQuery()) {
            id = request.getCallbackQuery().getMessage().getChatId();
        }else if (request.hasMessage()){
            id = request.getMessage().getChatId();
        }
        log.info("user id: {}", id);
        var user = findUserByTelegramUserId(id);
        return user.orElseGet(() -> createFromTelegramUpdate(request));
    }
}
