package com.vtb.dhbc.ClassDL;

import java.util.Random;

public class KeyGenerator {
    private static final String CHAR_POOL = "0123456789";
    private static final int KEY_LENGTH = 6;
    private static Random random = new Random();

    public static String generateKey() {
        StringBuilder key = new StringBuilder(KEY_LENGTH);
        for (int i = 0; i < KEY_LENGTH; i++) {
            key.append(CHAR_POOL.charAt(random.nextInt(CHAR_POOL.length())));
        }
        return key.toString();
    }
}
