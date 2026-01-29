package com.tietoevry.surest.member.management.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class LoginRequestTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private LoginRequest validRequest() {
        LoginRequest req = new LoginRequest();
        req.username = "admin";
        req.password = "adminpass";
        return req;
    }

    @Test
    void validRequest_shouldHaveNoViolations() {
        Set<ConstraintViolation<LoginRequest>> violations =
                validator.validate(validRequest());

        assertTrue(violations.isEmpty());
    }

    @Test
    void blankUsername_shouldFailValidation() {
        LoginRequest req = validRequest();
        req.username = "";

        Set<ConstraintViolation<LoginRequest>> violations =
                validator.validate(req);

        assertFalse(violations.isEmpty());
    }

    @Test
    void nullUsername_shouldFailValidation() {
        LoginRequest req = validRequest();
        req.username = null;

        Set<ConstraintViolation<LoginRequest>> violations =
                validator.validate(req);

        assertFalse(violations.isEmpty());
    }

    @Test
    void blankPassword_shouldFailValidation() {
        LoginRequest req = validRequest();
        req.password = " ";

        Set<ConstraintViolation<LoginRequest>> violations =
                validator.validate(req);

        assertFalse(violations.isEmpty());
    }

    @Test
    void nullPassword_shouldFailValidation() {
        LoginRequest req = validRequest();
        req.password = null;

        Set<ConstraintViolation<LoginRequest>> violations =
                validator.validate(req);

        assertFalse(violations.isEmpty());
    }
}
