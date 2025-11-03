package com.example.bruteforceauth.service;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
@Service
public class PasswordCreator {
    private static final String symbols = "QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm1234567890!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~";
    public static String createPassword() {
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            password.append(symbols.charAt(random.nextInt(symbols.length())));
        }
        if (passwordEntropy(password.toString())){
            return password.toString();
        }else {
            return createPassword();
        }
    }
    public static int charsetSizeMeasurement(String password) {
        boolean hasLowercase = false;
        boolean hasUppercase = false;
        boolean hasSpecial = false;
        boolean hasDigit = false;
        int charsetSize = 0;
        char[] measuringPassword = password.toCharArray();
        //can be a method
        for (int i = 0; i < measuringPassword.length; i++) {
            if (Character.isLowerCase(measuringPassword[i])) {
                hasLowercase = true;
            } else if (Character.isUpperCase(measuringPassword[i])) {
                hasUppercase = true;
            } else if (Character.isDigit(measuringPassword[i])) {
                hasDigit = true;
            } else {
                hasSpecial = true;
            }

        }
        if (hasLowercase){
            charsetSize+=26;
        }
        if (hasUppercase){
            charsetSize+=26;
        }
        if (hasSpecial){
            charsetSize+=32;
        }
        if (hasDigit){
            charsetSize+=10;
        }
        return charsetSize;
    }
    public static boolean passwordEntropy(String password) {
         int charsetSize = charsetSizeMeasurement(password);
         float entropy = (float) (16 * (Math.log(charsetSize) / Math.log(2)));
         if (entropy > 80) {
             return true;
         }
         return false;
    }

}
