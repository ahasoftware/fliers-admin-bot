package kz.aha.bot.data.bom.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.*;
@Data
@Entity
@Table(name = "fliers", schema = "comp_fliers")
@NoArgsConstructor
public class Fliers {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long user_id;

    @Column(name = "promo_code")
    private String promoCode;

    @Column(name = "counter")
    private Long counter;

    @Column(name = "agreement_id")
    private Long agreement_id;

    public Fliers(Long id) {
        this.id = id;
    }
}
