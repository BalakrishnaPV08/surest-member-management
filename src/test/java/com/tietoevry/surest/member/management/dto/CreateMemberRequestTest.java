package com.tietoevry.surest.member.management.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CreateMemberRequestTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private CreateMemberRequest validRequest() {
        CreateMemberRequest req = new CreateMemberRequest();
        req.firstName = "John";
        req.lastName = "Doe";
        req.dateOfBirth = LocalDate.of(1995, 1, 1);
        req.email = "john.doe@test.com";
        return req;
    }

    @Test
    void validRequest_shouldHaveNoViolations() {
        Set<ConstraintViolation<CreateMemberRequest>> violations =
                validator.validate(validRequest());

        assertTrue(violations.isEmpty());
    }

    @Test
    void blankFirstName_shouldFailValidation() {
        CreateMemberRequest req = validRequest();
        req.firstName = "";

        Set<ConstraintViolation<CreateMemberRequest>> violations =
                validator.validate(req);

        assertFalse(violations.isEmpty());
    }

    @Test
    void blankLastName_shouldFailValidation() {
        CreateMemberRequest req = validRequest();
        req.lastName = " ";

        Set<ConstraintViolation<CreateMemberRequest>> violations =
                validator.validate(req);

        assertFalse(violations.isEmpty());
    }

    @Test
    void nullDateOfBirth_shouldFailValidation() {
        CreateMemberRequest req = validRequest();
        req.dateOfBirth = null;

        Set<ConstraintViolation<CreateMemberRequest>> violations =
                validator.validate(req);

        assertFalse(violations.isEmpty());
    }

    @Test
    void invalidEmail_shouldFailValidation() {
        CreateMemberRequest req = validRequest();
        req.email = "invalid-email";

        Set<ConstraintViolation<CreateMemberRequest>> violations =
                validator.validate(req);

        assertFalse(violations.isEmpty());
    }

    @Test
    void blankEmail_shouldFailValidation() {
        CreateMemberRequest req = validRequest();
        req.email = "";

        Set<ConstraintViolation<CreateMemberRequest>> violations =
                validator.validate(req);

        assertFalse(violations.isEmpty());
    }
}

