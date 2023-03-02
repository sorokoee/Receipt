package com.clevertec.testapp.supermarket.validator.impl;

import com.clevertec.testapp.supermarket.validator.EmailValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class EmailValidatorImplTest {
    private EmailValidator emailValidator;

    @BeforeEach
    void setUp() {
        emailValidator = new EmailValidatorImpl();
    }

    @ParameterizedTest
    @MethodSource("getValidationArguments")
    void checkIsValid(String email, boolean expectedResult) {
        boolean actulResult = emailValidator.isValid(email);
        assertThat(actulResult).isEqualTo(expectedResult);

    }

    static Stream<Arguments> getValidationArguments() {
        return Stream.of(
                Arguments.of("ivanov@gmail.com", true),
                Arguments.of("sads1221@tut.by", true),
                Arguments.of("asd2@.@.com", false),
                Arguments.of("111asdas@ddd", false)
        );
    }

}