package Caprish.Model.imp.users;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "platform_admin")
@Getter @Setter
@NoArgsConstructor
public class PlatformAdmin extends User {

    public PlatformAdmin(String email, String encode) {
    }

}
