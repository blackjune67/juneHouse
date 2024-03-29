package com.junehouse.crypto;

import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class PasswordEncoder {

    private static final SCryptPasswordEncoder encoder = new SCryptPasswordEncoder(16, 8, 1, 32, 64);

    public String encrypt(String rawPassword) {
        return encoder.encode(rawPassword);
    }

    public boolean match(String rawPassword, String encryptedPassword) {
        return encoder.matches(rawPassword, encryptedPassword);
    }

}
