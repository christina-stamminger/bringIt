package com.codersnextdoor.bringIt.api.user.create;

import com.codersnextdoor.bringIt.api.address.Address;
import com.codersnextdoor.bringIt.api.address.AddressRepository;
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
    //Autowire for interacting with the database
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @PostMapping
    public ResponseEntity<UserResponseBody> create(
            @RequestBody
            CreateUserDTO createUserDTO) {

        UserResponseBody body = new UserResponseBody();


        if (createUserDTO == null || StringUtils.isEmpty(createUserDTO.getUsername())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        // Check if user exists
        Optional<User> optionalUser = this.userRepository.findByUsername(createUserDTO.getUsername());

        if(optionalUser.isPresent()) {
            body.addErrorMessage("User ist bereits in Verwendung");
            return new ResponseEntity<>(body, HttpStatus.CONFLICT);
        }
        // Check if address exists
        Optional<Address> optionalAddress = addressRepository.findById(createUserDTO.getAddressId());
        if(!optionalAddress.isPresent()) {
            body.addErrorMessage("Address not found");
            return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
        }
        // Retrieves the Address object from AddressRepository contained within the Optional<Address> if it is present.
       Address address = optionalAddress.get();

        // Create a new user object
        // Address object for associating user with address
        User user = new User(
                address,
                createUserDTO.getFirstName(),
                createUserDTO.getLastName(),
                createUserDTO.getUsername(),
                createUserDTO.getPasswordHash(),
                createUserDTO.getDateOfBirth(),
                createUserDTO.getEmail(),
                createUserDTO.getPhone()
        );

        // Save the new user to the repository
        try {
            this.userRepository.save(user);
        } catch(Throwable t) {
            body.addErrorMessage("Es ist ein Fehler aufgetreten");
            body.addErrorMessage(t.getMessage());

            return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // Return created user in response body
        body.setUser(user);
        return ResponseEntity.ok(body);
    }
}
