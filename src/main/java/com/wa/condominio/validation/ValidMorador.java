package com.wa.condominio.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MoradorValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidMorador {
    String message() default "Validação de Morador inválida.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
