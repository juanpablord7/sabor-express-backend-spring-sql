package com.miempresa.servicecategory.utils.annotation.handlerAnnotation;

import com.miempresa.servicecategory.utils.annotation.customAnnotation.NotXSS;
import com.miempresa.servicecategory.utils.validator.StringValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotXSSValidator implements ConstraintValidator<NotXSS, String> {
    // Este método es el que realiza la validación
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !StringValidator.validateXSS(value); // Si encuentra coincidencias con el patrón, es inválido
    }
}
