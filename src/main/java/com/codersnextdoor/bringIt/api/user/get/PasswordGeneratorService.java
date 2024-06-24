package com.codersnextdoor.bringIt.api.user.get;

import org.springframework.stereotype.Service;

@Service
public class PasswordGeneratorService {

    public String generatePassword(int length) {
        return PasswordGenerator.generateRandomPassword(length);
    }
}
