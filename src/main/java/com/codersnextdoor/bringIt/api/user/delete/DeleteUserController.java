package com.codersnextdoor.bringIt.api.user.delete;

import com.codersnextdoor.bringIt.api.ResponseBody;
import com.codersnextdoor.bringIt.api.user.User;
import com.codersnextdoor.bringIt.api.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class DeleteUserController {
    @Autowired
    private UserRepository userRepository;


    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseBody> delete(
            @PathVariable
            long id) {

        userRepository.deleteById(id);

        Optional<User> optionalUser = userRepository.findById(id);

        ResponseBody responseBody = new ResponseBody();

        if(optionalUser.isPresent()) {
            responseBody.addErrorMessage("could not delete user by id '" + id + "'.");
            return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
        } else {
            responseBody.addMessage("Ok");
            return new ResponseEntity<>(responseBody, HttpStatus.ACCEPTED);
        }
    }

    @DeleteMapping("/byUsername/{username}")
    public ResponseEntity<ResponseBody> deleteByUsername(
            @PathVariable
            String username) {

        Optional<User> optionalUser = this.userRepository.findByUsername(username);

        ResponseBody responseBody = new ResponseBody();

        if(optionalUser.isEmpty()) {
            responseBody.addErrorMessage("could not find user by username '" + username + "'.");
            return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
        }

        User user = optionalUser.get();

        this.userRepository.deleteById(user.getUserId());


        optionalUser = userRepository.findByUsername(username);

        if(optionalUser.isPresent()) {
            responseBody.addErrorMessage("could not delete user by username '" + username + "'.");
            return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
        } else {
            responseBody.addMessage("Ok");
            return new ResponseEntity<>(responseBody, HttpStatus.ACCEPTED);
        }
    }
}

