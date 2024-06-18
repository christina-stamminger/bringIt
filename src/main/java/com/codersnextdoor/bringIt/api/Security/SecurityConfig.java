package com.codersnextdoor.bringIt.api.Security;

import com.codersnextdoor.bringIt.api.user.UserRepository;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final RsaKeyProperties rsaKeys;
    private final UserRepository userRepository;
    // constructor injection for rsa keys and userRespository
    public SecurityConfig(RsaKeyProperties rsaKeys, UserRepository userRepository) {
        this.rsaKeys = rsaKeys;
        this.userRepository = userRepository;
    }
    // without password encoder
    /*
    @Bean
    public InMemoryUserDetailsManager user() {
        return new InMemoryUserDetailsManager(
                User.withUsername("Chris")
                        .password("{noop}password") // {} means password saved in plaintext
                        .authorities("read")
                        .build()
        );
    }
*/
    // configures the security settings of the app
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                // disable cross origin protection for stateless API
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth
                        // public endpoints accessible without authentication
                        .requestMatchers("/api/user/auth/login", "/api/user/signup").permitAll()
                        // all other endpoints require authentication
                        .anyRequest().authenticated()
                )
                // creating the resourceServer
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(jwtAuthenticationConverter()) // instead of ::jwt (method reference)
                        )
                )
                // turn off session management, we use rest api, we don't need a session
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // finally I need a way to authenticate users
                .httpBasic(withDefaults()) // import as a static import and not "Customizer.withDefaults()"
                .build();

    }

    @Bean
    public UserDetailsService customUserDetailsService() {
        // lambda exp, takes username who is trying to authenticate
        return username -> {
            // good practice to use the fully qualified class name (FQCN) when there's a possibility of ambiguity or when explicitly referencing a class from another package
            com.codersnextdoor.bringIt.api.user.User userEntity = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

            return User
                    .withUsername(userEntity.getUsername()) // Constructs a UserBuilder with the username obtained from the userEntity
                    .password(userEntity.getPassword()) // Sets the hashed pw for the user
                    .authorities("read") // Set user authorities/roles here
                    .accountExpired(false) // indicates if expired
                    .accountLocked(false) // ... or locked
                    .credentialsExpired(false) // ... or expired
                    .disabled(false) // ... or acc disabled
                    .build(); // finalizes construction of user object
        };
    }


/*
    @Bean
    public UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(
                User.withUsername("Chris")
                        .password(passwordEncoder().encode("password"))
                        .authorities("read")
                        .build()
        );
    }
*/

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // For encoding and decoding (verifying) jwt's
    // builder pattern from Nimbus JOSE + JWT library,
    // which allows you to construct a JWT decoder
    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(rsaKeys.publicKey()).build();
    }

    @Bean
    JwtEncoder jwtEncoder() {
        // JWK (JSON Web Key): constructs the RSA key pair (public and private keys).
        JWK jwk = new RSAKey.Builder(rsaKeys.publicKey()).privateKey(rsaKeys.privateKey()).build();
        // represents a Set of json web keys
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

    // Converts a JWT token into a Spring Security Authentication object
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        // Allows setting a custom converter for extracting authorities (roles) from the JWT token
        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            // Custom authorities extraction logic if needed
            return new ArrayList<>();
        });
        return converter;
    }


}
