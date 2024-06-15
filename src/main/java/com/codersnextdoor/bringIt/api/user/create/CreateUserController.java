package com.codersnextdoor.bringIt.api.user.create;

import com.codersnextdoor.bringIt.api.user.UserResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/signup")
public class CreateUserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseBody> create(@RequestBody CreateUserDTO createUserDTO) {
        UserResponseBody response = userService.createUser(createUserDTO);
        if (response.getMessage().isEmpty()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(409).body(response);
        }
    }



}


