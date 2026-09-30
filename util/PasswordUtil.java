package util;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PasswordUtil {

    private static final int ITERATIONS = 210000;

    private static final int KEY_LENGTH = 256;

    private static final int SALT_LENGTH = 16;


    // Generate a random salt
    public static String generateSalt() {

        byte[] salt = new byte[SALT_LENGTH];

        SecureRandom random = new SecureRandom();

        random.nextBytes(salt);

        return Base64.getEncoder()
                .encodeToString(salt);
    }


    // Hash the password
    public static String hashPassword(
            String password,
            String saltBase64) {

        try {

            byte[] salt =
                    Base64.getDecoder()
                            .decode(saltBase64);

            PBEKeySpec spec =
                    new PBEKeySpec(
                            password.toCharArray(),
                            salt,
                            ITERATIONS,
                            KEY_LENGTH
                    );

            SecretKeyFactory factory =
                    SecretKeyFactory.getInstance(
                            "PBKDF2WithHmacSHA256"
                    );

            byte[] hash =
                    factory.generateSecret(spec)
                            .getEncoded();

            spec.clearPassword();

            return Base64.getEncoder()
                    .encodeToString(hash);

        } catch (Exception e) {

            throw new RuntimeException(
                    "Password hashing failed",
                    e
            );
        }
    }


    // Verify password during login
    public static boolean verifyPassword(
            String password,
            String storedHash,
            String storedSalt) {

        String newHash =
                hashPassword(
                        password,
                        storedSalt
                );

        return MessageDigest.isEqual(
                Base64.getDecoder()
                        .decode(newHash),

                Base64.getDecoder()
                        .decode(storedHash)
        );
    }
}
