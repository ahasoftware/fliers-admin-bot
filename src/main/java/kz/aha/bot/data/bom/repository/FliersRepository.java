package kz.aha.bot.data.bom.repository;

import kz.aha.bot.data.bom.entity.Fliers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface FliersRepository extends JpaRepository<Fliers, Long>, JpaSpecificationExecutor<Fliers> {
    Optional<Fliers> findFliersByPromoCode(String promoCode);

    Optional<Fliers> findFliersByUserId(Long id);
}