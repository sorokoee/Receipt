package com.clevertec.testapp.supermarket.validator;

import java.util.regex.Pattern;

public interface EmailValidator {
    static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
            "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    static final Pattern pattern=Pattern.compile(EMAIL_REGEX);
    boolean isValid(String email);
}
