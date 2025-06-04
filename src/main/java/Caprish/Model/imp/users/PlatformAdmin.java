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
public class PlatformAdmin extends MyObject {

}
