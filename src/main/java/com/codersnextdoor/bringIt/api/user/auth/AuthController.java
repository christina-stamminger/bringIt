package com.codersnextdoor.bringIt.api.user.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/user/auth")
public class AuthController {

    // Logger for logging events
    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);
   // Service to generate tokens
    private final TokenService tokenService;
    // authenticationManager to handle logins
    // a spring security component to handle auth process
    private final AuthenticationManager authenticationManager;

    // constructor injection for dependencies
    // dependency Injection (DI) is a design pattern used to implement IoC (Inversion of Control).
    // it allows an object to receive its dependencies from an external source rather than creating them itself.
    // here spring Boot automatically injects instances of TokenService and AuthenticationManager into the AuthController when it is created.
    // This is called constructor-based dependency injection.
    public AuthController(TokenService tokenService, AuthenticationManager authenticationManager) {
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }
    // endpoint for user login
    @PostMapping("/login")
    public String login(@RequestBody LoginDTO loginDTO) {
        // authenticate the user using username and password
        Authentication authentication = authenticationManager.authenticate(
                // creates an authentication token using the username and password from the loginDTO
                new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword())
        );
        // sets the authentication object in the security context, marking the user as authenticated
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // logs the successful log in event
        LOG.debug("User logged in: {}", loginDTO.getUsername());
        return "Login successful";
    }

    @PostMapping("/token")
    public String token(Authentication authentication) {
        // logs a debug message
        LOG.debug("Token requested for user:{}", authentication.getName());
        // generates a token for auth user using tokenService
        String token = tokenService.generateToken(authentication);
        LOG.debug("Token granted {}", token);
        return token;
    }

}