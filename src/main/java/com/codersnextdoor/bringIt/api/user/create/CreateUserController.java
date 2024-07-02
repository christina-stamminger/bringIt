package com.codersnextdoor.bringIt.api.user.create;

import com.codersnextdoor.bringIt.api.user.UserResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class CreateUserController {

    private final UserService userService;

    @Autowired
    public CreateUserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserResponseBody> createUser(@RequestBody CreateUserDTO createUserDTO) {
        try {
            UserResponseBody responseBody = userService.createUser(createUserDTO);
            return new ResponseEntity<>(responseBody, HttpStatus.CREATED); // 201 Created
        } catch (UserService.UserAlreadyExistsException e) {
            UserResponseBody errorBody = new UserResponseBody();
            errorBody.addErrorMessage(e.getMessage());
            return new ResponseEntity<>(errorBody, HttpStatus.CONFLICT); // 409 Conflict
        } catch (Exception e) {
            UserResponseBody errorBody = new UserResponseBody();
            errorBody.addErrorMessage("An error occurred");
            errorBody.addErrorMessage(e.getMessage());
            return new ResponseEntity<>(errorBody, HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal Server Error
        }
    }
}


