package com.codersnextdoor.bringIt.api.user.auth;

import com.codersnextdoor.bringIt.api.user.User;
import com.codersnextdoor.bringIt.api.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
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

    private final UserRepository userRepository; // Inject UserRepository

    // constructor injection for dependencies
    // dependency Injection (DI) is a design pattern used to implement IoC (Inversion of Control).
    // it allows an object to receive its dependencies from an external source rather than creating them itself.
    // here spring Boot automatically injects instances of TokenService and AuthenticationManager into the AuthController when it is created.
    // This is called constructor-based dependency injection.
    public AuthController(TokenService tokenService, AuthenticationManager authenticationManager, UserRepository userRepository) {
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }
    // endpoint for user login

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login(@RequestBody LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        LOG.debug("User logged in: {}", loginDTO.getUsername());

        // Fetch user details including userId
        User user = userRepository.findByUsername(loginDTO.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found: " + loginDTO.getUsername()));

        System.out.println("User ID: " + user.getUserId());

        // Generate token
        String token = tokenService.generateToken(authentication);

        // Return token in JSON response
        return ResponseEntity.ok(new TokenResponseDTO(token, user.getUserId()));
    }
}