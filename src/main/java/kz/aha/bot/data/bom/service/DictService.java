package kz.aha.bot.data.bom.service;

import kz.aha.bot.util.record.InlineButton;
import org.jvnet.hk2.annotations.Service;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Akhmet.Sulemenov
 * created 28.06.2023
 */
@Service
public interface DictService {
    List<List<InlineButton>> getDictCompanies(String locale);
    public void sendAgreementToTable() throws NumberFormatException, SQLException;
}
