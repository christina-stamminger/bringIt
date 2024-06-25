package com.codersnextdoor.bringIt.api.user.get;

import java.security.SecureRandom;

public class PasswordGenerator {
//    private static final String ALLOWED_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+";
    private static final String ALLOWED_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!()%";
    public static String generateRandomPassword(int length) {
        if (length <= 0) {throw new IllegalArgumentException("Password length must be greater than 0");
        }
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < length; i++) {int randomIndex = random.nextInt(ALLOWED_CHARACTERS.length());
            password.append(ALLOWED_CHARACTERS.charAt(randomIndex));
        }
        System.out.println(password.toString());
        return password.toString();
    }
}
