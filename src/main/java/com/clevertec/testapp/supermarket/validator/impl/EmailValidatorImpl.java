package com.clevertec.testapp.supermarket.validator.impl;

import com.clevertec.testapp.supermarket.validator.EmailValidator;

import java.util.regex.Matcher;

public class EmailValidatorImpl implements EmailValidator {
    @Override
    public boolean isValid(String email) {
        Matcher matcher= pattern.matcher(email);
        return matcher.matches();
    }
}
