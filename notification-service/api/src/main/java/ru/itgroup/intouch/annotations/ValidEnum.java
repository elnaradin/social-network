package ru.itgroup.intouch.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.itgroup.intouch.validator.EnumValidator;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = EnumValidator.class)
public @interface ValidEnum {
    String message() default "Invalid value for enum type";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<? extends Enum<?>> enumClass();
}
