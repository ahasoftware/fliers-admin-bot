package kz.aha.bot.data.bom.repository;

import kz.aha.bot.data.bom.entity.Agreement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AgreementRepository extends JpaRepository<Agreement, Long>, JpaSpecificationExecutor<Agreement> {

}
