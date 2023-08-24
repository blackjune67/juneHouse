package com.junehouse.crypto;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


@Profile("test")
@Component
public class PlainPasswordEncoder implements PasswordEncoder {

    @Override
    public String encrypt(String rawPassword) {
        return rawPassword;
    }

    @Override
    public boolean match(String rawPassword, String encryptedPassword) {
        return rawPassword.matches(encryptedPassword);
    }
}
