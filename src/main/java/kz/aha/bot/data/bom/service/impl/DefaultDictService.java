package kz.aha.bot.data.bom.service.impl;

import kz.aha.bot.data.bom.entity.DictCompanies;
import kz.aha.bot.data.bom.repository.DictCompaniesRepository;
import kz.aha.bot.data.bom.service.DictService;
import kz.aha.bot.util.record.InlineButton;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
    private String parentCompany;
    private String childCompany;
    private String discount;
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

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

    public void processCallbackQuery(String query) throws NumberFormatException, SQLException {
        // check if parent company is empty
        if (this.parentCompany == null) {
            this.parentCompany = query;
        }
        // check if child company  is empty
        else if (this.childCompany == null) {
            this.childCompany = query;
        }
        else {
            this.discount = query;
            sendAgreementToTable();
            // reset all companies
            this.parentCompany = null;
            this.childCompany = null;
        }
    }

    private void sendAgreementToTable() throws NumberFormatException, SQLException {
        try (Connection connection = DriverManager.getConnection(this.url, this.username, this.password)) {
            String insertQuery = "INSERT INTO comp_fliers.agreement (comp_parent_id, comp_child_id, discount) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

            // Set values
            preparedStatement.setLong(1, Long.parseLong(this.parentCompany));   // parent company
            preparedStatement.setLong(2, Long.parseLong(this.childCompany));    // child company
            preparedStatement.setLong(3, Long.parseLong(this.discount));        // discount

            // Execute the query
            int rowsAffected = preparedStatement.executeUpdate();
            log.info("{} row(-s) were added to agreement table", rowsAffected);
        } catch (SQLException e) {
            log.info(e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}
