package kz.aha.bot.data.bom.service;

import org.jvnet.hk2.annotations.Service;

import java.sql.SQLException;

@Service
public interface AgreementService {
    void sendAgreementToTable() throws NumberFormatException, SQLException;
    long getDiscountById(long agreementId);
}
