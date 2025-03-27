package com.iss.constrains;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = CourseVideoTaskValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CourseVideoTaskConstraint {
    String message() default "One and only one of course, video, or task must be set.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
