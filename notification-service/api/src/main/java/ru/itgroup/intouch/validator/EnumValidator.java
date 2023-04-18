package ru.itgroup.intouch.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.jetbrains.annotations.NotNull;
import ru.itgroup.intouch.annotations.ValidEnum;

import java.util.ArrayList;
import java.util.List;

public class EnumValidator implements ConstraintValidator<ValidEnum, String> {
    private final List<String> values = new ArrayList<>();

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return values.contains(value);
    }

    @Override
    public void initialize(@NotNull ValidEnum constraintAnnotation) {
        for (Enum<?> type : constraintAnnotation.enumClass().getEnumConstants()) {
            values.add(type.name());
        }
    }
}
