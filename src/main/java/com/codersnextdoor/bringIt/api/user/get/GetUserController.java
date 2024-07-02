package com.codersnextdoor.bringIt.api.user.get;

import com.codersnextdoor.bringIt.api.user.User;
import com.codersnextdoor.bringIt.api.user.UserRepository;
import com.codersnextdoor.bringIt.api.user.UserResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/users")
public class GetUserController {

    @Autowired
    private UserRepository userRepository;
    //    @Autowired
//    private PasswordGeneratorService passwordGeneratorService;
    @Autowired
    private GetUserService getUserService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }


    @GetMapping("/username/{username}")
    public ResponseEntity<UserResponseBody> getByUsername(
            @PathVariable
            String username) {

        Optional<User> optionalUser = this.userRepository.findByUsername(username);

        if (optionalUser.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        User user = optionalUser.get();

        return ResponseEntity.ok(new UserResponseBody(user));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<UserResponseBody> getById(
            @PathVariable
            long id) {

        Optional<User> optionalUser = this.userRepository.findById(id);

        if (optionalUser.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        User user = optionalUser.get();

        return ResponseEntity.ok(new UserResponseBody(user));
    }


    @GetMapping("/getpassword/{username}")
    public ResponseEntity<UserResponseBody> getNewPassword(
            @PathVariable
            String username) {

        UserResponseBody userResponseBody = new UserResponseBody();

        // CHECK IF USER EXISTS
        Optional<User> optionalUser = this.userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            userResponseBody.addErrorMessage("User '" + username + "' doesn't exist.");
            return new ResponseEntity<>(userResponseBody, HttpStatus.NOT_FOUND);
        }

        User user = optionalUser.get();
        String oldPasswordHash = user.getPassword();

        // CREATE NEW PASSWORD
        String newPassword = getUserService.generatePassword();
        System.out.println("New Password: " + newPassword);


        // SAVE NEW PASSWORD TO DATABASE:
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);


        // CHECK IF SAVE WAS SUCCESSFUL:
        optionalUser = this.userRepository.findByUsername(user.getUsername());
        String savedUserPasswordHash = optionalUser.get().getPassword();
        if (savedUserPasswordHash.equals(oldPasswordHash)) {
            userResponseBody.addErrorMessage("New Password could not be saved.");
        }

        // CREATE AND SEND MAIL TO USER:
        getUserService.createNewLoginDataMail(user, newPassword);

        userResponseBody.setUser(user);

        // return success-message
        userResponseBody.addMessage("New LoginData were sent by mail to user " + user.getUsername()
                + ". to email: " + user.getEmail());
        return new ResponseEntity<>(userResponseBody, HttpStatus.OK);

    }


}
