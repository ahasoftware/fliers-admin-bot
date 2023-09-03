package kz.aha.bot.data.bom.service.impl;

import kz.aha.bot.data.bom.repository.FliersRepository;
import kz.aha.bot.data.bom.service.FliersService;
import kz.aha.bot.data.bom.entity.Fliers;

import lombok.RequiredArgsConstructor;
;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultFliersService implements FliersService {
   private final FliersRepository fliersRepository;

    public Long getAgreementID(String promoCode) {
        return fliersRepository.findFliersByPromoCode(promoCode).get().getAgreementId();
    }
}
