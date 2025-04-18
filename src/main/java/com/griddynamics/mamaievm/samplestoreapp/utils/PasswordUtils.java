package com.griddynamics.mamaievm.samplestoreapp.utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtils {

    private static final int BCRYPT_ROUNDS = 12; // Adjust as needed

    public static SaltedPassword hashPassword(String password) {
        String salt = BCrypt.gensalt(BCRYPT_ROUNDS);
        return SaltedPassword.builder()
                .hashedPassword(BCrypt.hashpw(password, salt))
                .salt(salt)
                .build();
    }

    public static boolean verifyPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}