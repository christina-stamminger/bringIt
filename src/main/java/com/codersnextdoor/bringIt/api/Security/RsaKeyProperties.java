package com.codersnextdoor.bringIt.api.Security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

// configuration properties class in Spring Boot that is used to map RSA key properties
// from the application's configuration file (application.properties) into Java objects
// a record is a new feature introduced in Java 14 that provides a concise way to declare classes
// that are transparent holders for immutable data.
// It automatically generates the following methods:
// A constructor that initializes the record's components.
// Getter methods for all the components.
// equals(), hashCode(), and toString() methods based on the components.
@ConfigurationProperties(prefix="rsa")
public record RsaKeyProperties(RSAPublicKey publicKey, RSAPrivateKey privateKey) {
}
