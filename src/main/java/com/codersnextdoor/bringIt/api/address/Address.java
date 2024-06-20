package com.codersnextdoor.bringIt.api.address;

import com.codersnextdoor.bringIt.api.todo.Todo;
import com.codersnextdoor.bringIt.api.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.Set;

import static org.hibernate.annotations.OnDeleteAction.SET_NULL;

@Setter
@Getter
@Entity
@Table(name= "TB_ADDRESS")
public class Address {
    @Id
    @GeneratedValue(generator = "address-sequence-generator")
    @GenericGenerator(
            name = "address-sequence-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "address_sequence"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @Column(name = "address_id")
    private long addressId;

    @OneToMany(mappedBy = "address"   /* , cascade = CascadeType.ALL, orphanRemoval = true  */)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Set<User> users;

    private String streetNumber;

    private String postalCode;

    private String city;


    // empty constructor
    public Address() {

    }

    // constructor
    public Address(Set<User> users, String streetNumber, String postalCode, String city, LocalDateTime createdAt) {

        this.users = users;
        this.streetNumber = streetNumber;
        this.postalCode = postalCode;
        this.city = city;
    }

    // constructor

    public Address(String streetNumber, String postalCode, String city) {
        this.streetNumber = streetNumber;
        this.postalCode = postalCode;
        this.city = city;
    }


    // getter and setter by lombok

}

