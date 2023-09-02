package kz.aha.bot.data.bom.service.impl;

import kz.aha.bot.data.bom.entity.DictCompanies;
import kz.aha.bot.data.bom.repository.DictCompaniesRepository;
import kz.aha.bot.data.bom.service.DictService;
import kz.aha.bot.util.record.InlineButton;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Akhmet.Sulemenov
 * created 28.06.2023
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Getter
@Setter
public class DefaultDictService implements DictService {
    private final DictCompaniesRepository dictCompaniesRepository;

    public List<List<InlineButton>> getDictCompanies(String locale) {
        List<DictCompanies> companies = dictCompaniesRepository.findAll();  // get all rows from table
        List<InlineButton> buttonsList = new ArrayList<>();
        for (DictCompanies company : companies) {       //get all company names by language
            String name = switch (locale) {
                case "en" -> company.getNameEn();
                case "ru" -> company.getNameRu();
                default -> company.getNameKz();
            };
            buttonsList.add(new InlineButton(name, String.valueOf(company.getId())));
        }
        List<List<InlineButton>> toReply = new ArrayList<>();
        toReply.add(buttonsList);
        return toReply;
    }
}
