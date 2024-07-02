package com.codersnextdoor.bringIt.api.user.create;

import com.codersnextdoor.bringIt.api.address.Address;
import com.codersnextdoor.bringIt.api.address.AddressRepository;
import com.codersnextdoor.bringIt.api.address.create.CreateAddressDTO;
import com.codersnextdoor.bringIt.api.user.User;
import com.codersnextdoor.bringIt.api.user.UserRepository;
import com.codersnextdoor.bringIt.api.user.UserResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, AddressRepository addressRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponseBody createUser(CreateUserDTO createUserDTO) throws UserAlreadyExistsException {
        UserResponseBody body = new UserResponseBody();

        if (createUserDTO == null || StringUtils.isEmpty(createUserDTO.getUsername())) {
            body.addErrorMessage("Invalid user data");
            return body;
        }

        // Check if user exists
        Optional<User> optionalUser = userRepository.findByUsername(createUserDTO.getUsername());
        if (optionalUser.isPresent()) {
            throw new UserAlreadyExistsException("User already exists.");
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
        User user = new User();
        user.setAddress(address);
        user.setUsername(createUserDTO.getUsername());
        user.setPassword(passwordEncoder.encode(createUserDTO.getPassword()));
        user.setFirstName(createUserDTO.getFirstName());
        user.setLastName(createUserDTO.getLastName());
        user.setDateOfBirth(createUserDTO.getDateOfBirth());
        user.setEmail(createUserDTO.getEmail());
        user.setPhone(createUserDTO.getPhone());
        user.setBringIts(0);

        // Save the new user to the repository
        try {
            userRepository.save(user);
            body.setUser(user);
        } catch (Throwable t) {
            body.addErrorMessage("An error occurred");
            body.addErrorMessage(t.getMessage());
        }

        return body;
    }

    // Custom exception for user already exists scenario
    public static class UserAlreadyExistsException extends RuntimeException {
        public UserAlreadyExistsException(String message) {
            super(message);
        }
    }
}


