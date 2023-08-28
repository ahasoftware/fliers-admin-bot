package kz.aha.bot.data.bom.entity;

import kz.aha.bot.util.enums.RoleType;
import lombok.*;

import javax.persistence.*;

/**
 * @author Akhmet.Sulemenov
 * created 28.06.2023
 */
@Getter
@Setter
@Entity
@Table(name = "users_role", schema = "auth_fliers")
@NoArgsConstructor
@Builder @AllArgsConstructor
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private RoleType role;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public UserRole(RoleType role) {
        this.role = role;
    }
}



