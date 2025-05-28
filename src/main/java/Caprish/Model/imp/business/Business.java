package Caprish.Model.imp.business;

import Caprish.Model.imp.MyObject;
import Caprish.Model.imp.admin.BusinessReport;
import Caprish.Model.imp.messaging.Chat;
import Caprish.Model.imp.messaging.Message;
import Caprish.Model.imp.users.Staff;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name="business")
public class Business extends MyObject {
    @Column(unique=true,columnDefinition="TEXT",nullable=false)
    private String bussiness_name;

    @Column(nullable=false,columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false,columnDefinition = "TEXT")
    private String slogan;

    @Column(nullable = false)
    @NotBlank(message="El cuit no puede estar vacio")
    private Integer tax;

    @OneToMany(mappedBy = "business", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("chat-business")
    private List<Chat> chats = new ArrayList<>();

    @OneToMany(mappedBy = "business", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("staff-business")
    private List<Staff> staff = new ArrayList<>();

    @OneToMany(mappedBy = "business", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("product-business")
    private List<Product> products = new ArrayList<>();

    @OneToMany(mappedBy = "business", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("branch-business")
    private List<Branch> branches = new ArrayList<>();

    @OneToMany(mappedBy = "business", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("businessReport-business")
    private List<BusinessReport> reports = new ArrayList<>();
}
