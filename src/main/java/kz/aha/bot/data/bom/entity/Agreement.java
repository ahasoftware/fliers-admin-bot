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
@Entity
@Table(name = "agreement", schema = "comp_fliers")
@NoArgsConstructor
@Data
public class Agreement {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "comp_parent_id")
    private Long comp_parent_id;

    @Column(name = "comp_child_id")
    private Long comp_child_id;

    @Column(name = "discount")
    private Long discount;
    public Agreement(Long id) {
        this.id = id;
    }

}

