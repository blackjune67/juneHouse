package com.junehouse.crypto;

public interface PasswordEncoder {
    String encrypt(String rawPassword);

    boolean match(String rawPassword, String encryptedPassword);
}
