package com.codersnextdoor.bringIt.api.user.update;

import com.codersnextdoor.bringIt.api.user.User;
import com.codersnextdoor.bringIt.api.user.UserRepository;
import com.codersnextdoor.bringIt.api.user.UserResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UpdateUserController {

    @Autowired
    UserRepository userRepository;


    @PutMapping
    public ResponseEntity<UserResponseBody> update(
            @RequestBody
            UpdateUserDTO updateUserDTO) {

        if (updateUserDTO == null || updateUserDTO.getUserId() < 1) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        Optional<User> optionalUser = this.userRepository.findById(updateUserDTO.getUserId());

        if (optionalUser.isEmpty()) {
            UserResponseBody response = new UserResponseBody();
            response.addErrorMessage("could not find user by id '" + updateUserDTO.getUserId());

            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        User user = optionalUser.get();

        if(!StringUtils.isEmpty(updateUserDTO.getFirstName())) {
            user.setFirstName(updateUserDTO.getFirstName());
        }


        if(!StringUtils.isEmpty(updateUserDTO.getLastName())) {
            user.setLastName(updateUserDTO.getLastName());
        }

        this.userRepository.save(user);

        UserResponseBody response = new UserResponseBody();
        response.setUser(user);

        return ResponseEntity.ok(response);
    }
}
