package com.example.Study.Hall.Management.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneNumberValidator {

    public static boolean validatePhoneNumber(String phoneNumber) {
        String regex = "^([7-9][0-9]{9})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }
}
