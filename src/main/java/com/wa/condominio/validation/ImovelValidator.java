package com.wa.condominio.validation;

import com.wa.condominio.dto.ImovelDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ImovelValidator implements ConstraintValidator<ValidImovel, ImovelDTO> {

    @Override
    public boolean isValid(ImovelDTO dto, ConstraintValidatorContext context) {
        if (dto == null) {
            return true;
        }

        boolean isValid = true;

        if (dto.getBloco() != null) {
            if (dto.getBloco().length() < 1 || dto.getBloco().length() > 10) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("O campo Bloco precisa ter entre 1 e 10 caracteres.")
                        .addPropertyNode("bloco")
                        .addConstraintViolation();
                isValid = false;
            }
        }

        if (dto.getApartamento() != null) {
            if (dto.getApartamento().length() < 1 || dto.getApartamento().length() > 10) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("O campo Apartamento precisa ter entre 1 e 10 caracteres.")
                        .addPropertyNode("apartamento")
                        .addConstraintViolation();
                isValid = false;
            }
        }

        if (dto.getBoxGaragem() != null) {
            if (dto.getBoxGaragem().length() < 1 || dto.getBoxGaragem().length() > 10) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("O campo Box Garagem precisa ter entre 1 e 10 caracteres.")
                        .addPropertyNode("boxGaragem")
                        .addConstraintViolation();
                isValid = false;
            }
        }

        return isValid;
    }
}
