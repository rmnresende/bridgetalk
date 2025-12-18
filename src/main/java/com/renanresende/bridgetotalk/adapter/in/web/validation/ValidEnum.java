package com.renanresende.bridgetotalk.adapter.in.web.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({
        ElementType.FIELD,
        ElementType.PARAMETER,
        ElementType.METHOD,
        ElementType.ANNOTATION_TYPE
})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumValueValidator.class)
@Documented
public @interface ValidEnum {

    Class<? extends Enum<?>> enumClass();
    String message() default "Invalid value for {0}.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}