package kz.aha.bot.data.bom.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kz.aha.bot.data.common.DateAudit;
import kz.aha.bot.util.enums.LanguageMode;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

/**
 * @author Akhmet.Sulemenov
 * created 28.06.2023
 */
@Getter
@Setter
@Entity
@Table(name = "agreement", schema = "dict_fliers")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Agreement {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "comp_first_id")
    private Long comp_first_id;

    @Column(name = "comp_second_id")
    private Long comp_second_id;
    public Agreement(Long id) {
        this.id = id;
    }

}

