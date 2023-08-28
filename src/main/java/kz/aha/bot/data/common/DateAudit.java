package kz.aha.bot.data.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Akhmet.Sulemenov
 * created 28.06.2023
 */
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(
        value = {"createDate", "lastUpdateDate"},
        allowGetters = true
)
public abstract class DateAudit implements Serializable {

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    @Column(name = "create_date", nullable = false, updatable = false)
    private Date createDate;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    @Column(name = "last_update_date", nullable = false)
    private Date lastUpdateDate;
}
