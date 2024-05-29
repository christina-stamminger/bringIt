package com.codersnextdoor.bringIt.api.address;

import com.codersnextdoor.bringIt.api.user.User;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

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

    private long addressId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String streetNumber;

    private String postalCode;

    private String city;

    @CreationTimestamp
    @Column(name="created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

}

