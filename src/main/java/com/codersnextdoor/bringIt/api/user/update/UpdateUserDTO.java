package com.codersnextdoor.bringIt.api.user.update;

import com.codersnextdoor.bringIt.api.address.Address;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class UpdateUserDTO {

    // getters and setters
    private long userId;

    private Address address;

    private String firstName;

    private String lastName;
    private LocalDate dateOfBirth;

    private String email;
    private String password;

    private String phone;


}
