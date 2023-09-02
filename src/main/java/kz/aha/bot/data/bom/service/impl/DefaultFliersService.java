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
   long answer = 0;
   long counter;




    @Override
    public Long getAgreementID(String promoCode) {
        List<Fliers> fliers = fliersRepository.findAll();

        for (Fliers flier : fliers) {
            if(flier.getPromoCode().equals(promoCode)){
                answer = flier.getAgreement_id();
                counter = flier.getCounter();
                counter++;
                flier.setCounter(counter);
            }
        }

        return answer;
    }
}
