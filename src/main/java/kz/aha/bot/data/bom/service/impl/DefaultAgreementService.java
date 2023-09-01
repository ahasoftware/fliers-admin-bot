package kz.aha.bot.data.bom.service.impl;

import kz.aha.bot.data.bom.service.AgreementService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Slf4j
@Service
@RequiredArgsConstructor
@Getter
@Setter
public class DefaultAgreementService implements AgreementService {
    private String parentCompany;
    private String childCompany;
    private String discount;
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    public void sendAgreementToTable() throws NumberFormatException, SQLException {
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
        this.resetFields();
    }

    public void resetFields(){
        this.setParentCompany(null);
        this.setChildCompany(null);
        this.setDiscount(null);
    }
}
