package com.renanresende.bridgetotalk.adapter.in.web.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;

/**
 * Validador para a anotação @ValidEnum.
 * Valida se um valor string corresponde a um dos valores do enum especificado.
 */
public class EnumValueValidator implements ConstraintValidator<ValidEnum, String> {

    private List<String> acceptedValues;

    @Override
    public void initialize(ValidEnum annotation) {
        acceptedValues = Arrays.stream(annotation.enumClass().getEnumConstants())
                .map(Enum::name)
                .toList();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (value == null) {
            return true;
        }

        return acceptedValues.contains(value.toUpperCase()); // exemplo case-insensitive
    }
}