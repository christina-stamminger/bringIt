package com.codersnextdoor.bringIt.api.user.get;

import com.codersnextdoor.bringIt.api.user.User;
import com.codersnextdoor.bringIt.api.user.UserRepository;
import com.codersnextdoor.bringIt.api.user.UserResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @Autowired
    private PasswordGeneratorService passwordGeneratorService;

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

        if(!optionalUser.isPresent()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        User user = optionalUser.get();

        return ResponseEntity.ok(new UserResponseBody(user));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<UserResponseBody> getById(
            @PathVariable
            long id) {

        Optional<User> optionalUser = this.userRepository.findById(id);

        if(!optionalUser.isPresent()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        User user = optionalUser.get();

        return ResponseEntity.ok(new UserResponseBody(user));
    }



//    @GetMapping("/getpassword/{username}")
//    public ResponseEntity<User> getNewPassword(
//                @PathVariable
//                String username) {
//
//        UserResponseBody userResponseBody = new UserResponseBody();

        // CHECK IF USER EXISTS
//        Optional<User> optionalUser = this.userRepository.findByUsername(username);
//        if (optionalUser.isEmpty()) {
//            userResponseBody.addErrorMessage("A user with the name " + username + " doesn't exist.");
//            return new ResponseEntity(userResponseBody, HttpStatus.NOT_FOUND);
//        }
//
//        User user = optionalUser.get();

        // CREATE NEW PASSWORD


    private static final int PASSWORD_LENGTH = 12; // Fixed password length


    @GetMapping("/generate-password")
    public ResponseEntity<String> generatePassword() {
        String pw = passwordGeneratorService.generatePassword(PASSWORD_LENGTH);
        System.out.println("New Password: " + pw);
        return ResponseEntity.ok(pw);
    }


}
