package kz.aha.bot.data.bom.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author Akhmet.Sulemenov
 * created 28.06.2023
 */
@Data
@Entity
@Table(name = "dict_companies", schema = "dict_fliers")
@NoArgsConstructor
public class DictCompanies implements Serializable {

    public static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "code", length = 30)
    private String code;

    @Column(name = "name_ru")
    private String nameRu;

    @Column(name = "name_kz")
    private String nameKz;

    @Column(name = "name_en")
    private String nameEn;

    public DictCompanies(Long id) {
        this.id = id;
    }
}
