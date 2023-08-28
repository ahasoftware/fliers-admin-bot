package kz.aha.bot.data.bom.repository;

import kz.aha.bot.data.bom.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Akhmet.Sulemenov
 * created 28.06.2023
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    Optional<User> findByUserId(Long id);

    Optional<User> findByPhoneNumber(String phone);

    Optional<User> findUserByUserId(Long userId);
}
