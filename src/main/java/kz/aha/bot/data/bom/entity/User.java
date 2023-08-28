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
@Table(name = "users", schema = "auth_fliers")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User extends DateAudit {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "t_user_id")
    private Long userId;

    @Column(name = "t_user_name")
    private String tUserName;

    @Column(name = "t_first_name")
    private String tFirstName;

    @Column(name = "lang")
    @Enumerated(EnumType.STRING)
    private LanguageMode languageMode;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "previous_message")
    private String previousMessage;

    @JsonIgnore
    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "user",
            orphanRemoval = true,
            fetch= FetchType.EAGER
    )
    @Fetch(value = FetchMode.SUBSELECT)
    private List<UserRole> userRoles;

}

