package com.wa.condominio.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import com.wa.condominio.dto.MoradorDTO;

public class MoradorValidator implements ConstraintValidator<ValidMorador, MoradorDTO> {

    @Override
    public boolean isValid(MoradorDTO dto, ConstraintValidatorContext context) {
        if (dto == null) return true;

        if (dto.getDataEntrada() != null && dto.getDataSaida() != null) {
            if (!dto.getDataSaida().isAfter(dto.getDataEntrada())) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("Data de saída deve ser maior que a data de entrada.")
                        .addPropertyNode("dataSaida")
                        .addConstraintViolation();
                return false;
            }
        }
        return true;
    }
}
