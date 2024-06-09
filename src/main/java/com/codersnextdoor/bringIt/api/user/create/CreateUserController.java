package com.codersnextdoor.bringIt.api.user.create;

import com.codersnextdoor.bringIt.api.address.Address;
import com.codersnextdoor.bringIt.api.address.AddressRepository;
import com.codersnextdoor.bringIt.api.address.create.CreateAddressDTO;
import com.codersnextdoor.bringIt.api.user.User;
import com.codersnextdoor.bringIt.api.user.UserRepository;
import com.codersnextdoor.bringIt.api.user.UserResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/user/")
public class CreateUserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @PostMapping
    public ResponseEntity<UserResponseBody> create(@RequestBody CreateUserDTO createUserDTO) {

        UserResponseBody body = new UserResponseBody();

        if (createUserDTO == null || StringUtils.isEmpty(createUserDTO.getUsername())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Check if user exists
        Optional<User> optionalUser = userRepository.findByUsername(createUserDTO.getUsername());
        if (optionalUser.isPresent()) {
            body.addErrorMessage("User already exists.");
            return new ResponseEntity<>(body, HttpStatus.CONFLICT);
        }

        // Extract address from DTO
        CreateAddressDTO addressDTO = createUserDTO.getAddress();

        // Check if address exists
        Optional<Address> optionalAddress = addressRepository.findByStreetNumberAndPostalCodeAndCity(
                addressDTO.getStreetNumber(),
                addressDTO.getPostalCode(),
                addressDTO.getCity()
        );

        Address address;
        if (optionalAddress.isPresent()) {
            address = optionalAddress.get();
        } else {
            address = new Address(
                    addressDTO.getStreetNumber(),
                    addressDTO.getPostalCode(),
                    addressDTO.getCity()
            );
            addressRepository.save(address);
        }

        // Create a new user object
        User user = new User(
                address,
                createUserDTO.getUsername(),
                createUserDTO.getPassword(),
                createUserDTO.getFirstName(),
                createUserDTO.getLastName(),
                createUserDTO.getDateOfBirth(),
                createUserDTO.getEmail(),
                createUserDTO.getPhone(),
                createUserDTO.getCreatedAt()
        );

        // Save the new user to the repository
        try {
            userRepository.save(user);
        } catch (Throwable t) {
            body.addErrorMessage("An error occurred");
            body.addErrorMessage(t.getMessage());
            return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // Return created user in response body
        body.setUser(user);
        return ResponseEntity.ok(body);
    }
}
