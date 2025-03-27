package com.iss.constrains;

import com.iss.models.Payment;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CourseVideoTaskValidator implements ConstraintValidator<CourseVideoTaskConstraint, Payment> {

    @Override
    public boolean isValid(Payment payment, ConstraintValidatorContext context) {
        int nonNullCount = 0;
        
        if (payment.getUsercourse() != null) nonNullCount++;
        if (payment.getUservedio() != null) nonNullCount++;
        if (payment.getUsertask() != null) nonNullCount++;
        
        // Ensure exactly one of them is non-null
        return nonNullCount == 1;
    }
}

