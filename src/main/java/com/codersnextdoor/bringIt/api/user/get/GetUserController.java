package com.codersnextdoor.bringIt.api.user.get;

import com.codersnextdoor.bringIt.api.user.User;
import com.codersnextdoor.bringIt.api.user.UserRepository;
import com.codersnextdoor.bringIt.api.user.UserResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class GetUserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/api/users/")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/api/users/username/{username}")
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

    @GetMapping("/api/users/id/{id}")
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
}
