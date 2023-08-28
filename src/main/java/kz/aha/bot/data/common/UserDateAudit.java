package kz.aha.bot.data.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * @author Akhmet.Sulemenov
 * created 28.06.2023
 */
@Data
@MappedSuperclass
@JsonIgnoreProperties(
        value = {"createBy", "lastUpdateBy"},
        allowGetters = true
)
public abstract class UserDateAudit extends DateAudit {

    @CreatedBy
    @Column(name = "create_by", updatable = false)
    private String createBy;

    @LastModifiedBy
    @Column(name = "last_update_by")
    private String lastUpdateBy;
}
