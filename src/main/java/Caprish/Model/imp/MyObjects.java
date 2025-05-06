package Caprish.Model.imp;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
public class MyObjects {
        @Id
        @GeneratedValue(strategy=GenerationType.IDENTITY)
        private Long id;

}



