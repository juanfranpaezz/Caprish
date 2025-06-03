package Caprish.Model.imp.users;

import Caprish.Model.imp.MyObject;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "platform_admin")
@Getter @Setter
@NoArgsConstructor
public class PlatformAdmin extends MyObject {
    @OneToOne(optional = false)
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    public PlatformAdmin(User user) {
        this.user = user;
    }
}
