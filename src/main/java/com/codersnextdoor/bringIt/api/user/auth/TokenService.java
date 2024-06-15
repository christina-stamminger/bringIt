package com.codersnextdoor.bringIt.api.user.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

// Mark this class as a Spring service,
// which makes it a candidate for Spring's component scanning to detect
// and register as a bean.
@Service
public class TokenService {
    // Declare a final JwtEncoder field to hold the JwtEncoder instance injected via constructor
    private final JwtEncoder encoder;
    // Constructor to initialize the JwtEncoder
    // Spring will inject the JwtEncoder bean here
    public TokenService(JwtEncoder encoder) {
        this.encoder = encoder;
    }


    // Method to generate a JWT token for the authenticated user
    public String generateToken(Authentication authentication) {
        // Get the current instant (timestamp) to set the token issuance time.
        Instant now = Instant.now();
        // Collect the authorities (roles/permissions) of the authenticated user into a single string
        String scope = authentication.getAuthorities().stream()
         // Extract the authority (role/permission) name from each GrantedAuthority
        .map(GrantedAuthority::getAuthority)
        // Join all authorities into a single string separated by spaces
        .collect(Collectors.joining(""));
        // builds a JwtClaimsSet object, which holds the claims (information) to be included in the JWT
    JwtClaimsSet claims = JwtClaimsSet.builder()
            .issuer("self") // Sets the issuer of the token. "self" indicates that the application itself is issuing the token
            .issuedAt(now) // Sets the issuance time of the token to the current timestamp
            .expiresAt(now.plus(1, ChronoUnit.HOURS)) // Sets the expiration time of the token to one hour from the current timestamp
        .subject(authentication.getName()) // sets the subject (here username) of the token
                .claim("scope", scope) // adds a custom claim to the token, claim is named "scope" and its value is the string of user authorities
        .build(); // builds the jwt claim object
    return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue(); // encodes the JwtClaimsSet into a JWT token using the JwtEncoder and returns the token value as a string
    }
}

