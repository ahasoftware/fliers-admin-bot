package kz.aha.bot.data.bom.repository;

import kz.aha.bot.data.bom.entity.DictCompanies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DictCompaniesRepository extends JpaRepository<DictCompanies, Long>, JpaSpecificationExecutor<DictCompanies> {

}
