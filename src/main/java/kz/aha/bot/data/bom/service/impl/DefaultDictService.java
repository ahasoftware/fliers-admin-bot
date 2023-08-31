package kz.aha.bot.data.bom.service.impl;

import kz.aha.bot.data.bom.entity.DictCompanies;
import kz.aha.bot.data.bom.repository.DictCompaniesRepository;
import kz.aha.bot.data.bom.service.DictService;
import kz.aha.bot.util.record.InlineButton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Akhmet.Sulemenov
 * created 28.06.2023
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultDictService implements DictService {
    private final DictCompaniesRepository dictCompaniesRepository;
    public  List<List<InlineButton>> getDictCompanies(String locale){
        List<DictCompanies> companies = dictCompaniesRepository.findAll();  // get all rows from table
        List<InlineButton> buttonsList = new ArrayList<>();
        switch (locale) {
            case "en" -> {
                for (DictCompanies company : companies) {
                    var button = new InlineButton(company.getNameEn(), String.valueOf(company.getId()));
                    buttonsList.add(button);
                }
            }
            case "ru" -> {
                for (DictCompanies company : companies) {
                    var button = new InlineButton(company.getNameRu(), String.valueOf(company.getId()));
                    buttonsList.add(button);
                }
            }
            default -> {
                for (DictCompanies company : companies) {
                    var button = new InlineButton(company.getNameKz(), String.valueOf(company.getId()));
                    buttonsList.add(button);
                }
            }
        }
        List<List<InlineButton>> toReply = new ArrayList<>();
        toReply.add(buttonsList);
        return toReply;
    }
}
