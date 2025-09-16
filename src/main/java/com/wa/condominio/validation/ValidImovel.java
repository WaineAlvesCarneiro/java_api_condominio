package com.wa.condominio.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ImovelValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidImovel {
    String message() default "Validação de Imóvel inválida.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
