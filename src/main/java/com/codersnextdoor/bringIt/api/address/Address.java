package com.codersnextdoor.bringIt.api.address;

import com.codersnextdoor.bringIt.api.todo.Todo;
import com.codersnextdoor.bringIt.api.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.Set;

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

    @OneToMany(mappedBy = "address", cascade = CascadeType.ALL, orphanRemoval = true)
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


    // getter and setter

    public long getAddressId() {
        return addressId;
    }

    public void setAddressId(long addressId) {
        this.addressId = addressId;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

}

