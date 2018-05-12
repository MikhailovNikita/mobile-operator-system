package ru.tsystems.utils;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * Util for generating passwords
 */
public class PasswordGenerator {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_";

    /**
     * Generates password consisting of 6 chars from CHARACTERS string
     * @return generated password
     */
    public static String generatePassword(){
        return RandomStringUtils.random(6, CHARACTERS);
    }

    /**
     * Generates password consisting of N chars from CHARACTERS string
     * @param n length of generated password
     * @return generated password
     */
    public static String generatePassword(int n){
        return RandomStringUtils.random(n, CHARACTERS);
    }

    private PasswordGenerator() {
    }
}
