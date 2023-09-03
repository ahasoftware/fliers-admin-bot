package kz.aha.bot.data.bom.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.*;

@Data
@Entity
@Table(name = "fliers", schema = "comp_fliers")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Fliers {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "promo_code")
    private String promoCode;

    @Column(name = "counter")
    private Long counter;

    @Column(name = "agreement_id")
    private Long agreementId;

//    @ToString.Exclude
//    @ManyToOne
//    @JoinColumn(name = "agreement_id")
//    private Agreement agreement;
}
