package kz.aha.bot.data.bom.repository;

import kz.aha.bot.data.bom.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author Akhmet.Sulemenov
 * created 28.06.2023
 */
@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long>, JpaSpecificationExecutor<UserRole> {
}
