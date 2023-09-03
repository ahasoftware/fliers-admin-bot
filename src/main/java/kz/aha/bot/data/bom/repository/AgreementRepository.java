package kz.aha.bot.data.bom.repository;

import kz.aha.bot.data.bom.entity.Agreement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface AgreementRepository extends JpaRepository<Agreement, Long>, JpaSpecificationExecutor<Agreement> {
    Optional<Agreement> findAgreementById(Long id);
}
