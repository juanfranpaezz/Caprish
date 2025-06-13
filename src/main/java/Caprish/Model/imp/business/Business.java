package Caprish.Model.imp.business;

import Caprish.Model.imp.MyObject;
import Caprish.Model.imp.messaging.Chat;
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
    private String businessName;

    @Embedded
    private Address address;

    @Column(nullable=false,columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false,columnDefinition = "TEXT")
    private String slogan;

    @Column(nullable = false)
    private Long tax;

    @OneToMany(mappedBy = "business", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("chat-business")
    private List<Chat> chats = new ArrayList<>();

    @OneToMany(mappedBy = "business", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("staff-business")
    private List<Staff> staff = new ArrayList<>();

    @OneToMany(mappedBy = "business", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("product-business")
    private List<Product> products = new ArrayList<>();

}
