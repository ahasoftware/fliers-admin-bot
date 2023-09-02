package kz.aha.bot.data.bom.repository;

import kz.aha.bot.data.bom.entity.Fliers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FliersRepository extends JpaRepository<Fliers, Long>, JpaSpecificationExecutor<Fliers> {

}