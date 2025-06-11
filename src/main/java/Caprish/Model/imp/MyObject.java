package Caprish.Model.imp;

import jakarta.persistence.*;
import lombok.*;

@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MyObject{
        @Id
        @GeneratedValue(strategy=GenerationType.IDENTITY)
        protected Long id;

}




