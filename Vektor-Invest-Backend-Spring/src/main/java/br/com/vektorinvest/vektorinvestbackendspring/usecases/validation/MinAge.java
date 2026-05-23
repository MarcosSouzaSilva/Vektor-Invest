package br.com.vektorinvest.vektorinvestbackendspring.usecases.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MinAgeValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MinAge {

    int value();

    String message() default "Idade mínima não atendida";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
