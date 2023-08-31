package kz.aha.bot.data.bom.service;

import kz.aha.bot.util.record.InlineButton;

import java.util.List;

/**
 * @author Akhmet.Sulemenov
 * created 28.06.2023
 */
public interface DictService {
    List<List<InlineButton>> getDictCompanies(String locale);
}
