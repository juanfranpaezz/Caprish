package Caprish.Model.imp.admin;

import Caprish.Model.imp.MyObjects;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name="BusinnesReport")
public class BusinessReport extends MyObjects {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id_business_report;
    @Column (nullable=false)
    private Long id_business;
    @Column (nullable=false)
    private LocalDate generated_at;
    @Column (columnDefinition= "TEXT")
    private String description;
    @NotBlank(message="aaa")
    private String about;

//    String sql =    "CREATE TABLE business (" +
//                    "id_business_report BIGINT PRIMARY KEY AUTO_INCREMENT," +
//                    "id_business INT NOT NULL," +
//                    "generated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,\n" +
//                    "CONSTRAINT id_business FOREIGN KEY (id_business)" +
//                    "REFERENCES business(id_business)" +
//                    "about VARCHAR(200), NOT NULL" +
//                    "description TEXT" +
//                    ");";
}
