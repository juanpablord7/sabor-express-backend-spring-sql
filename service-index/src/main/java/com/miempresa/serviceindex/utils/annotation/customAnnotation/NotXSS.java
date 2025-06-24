package com.miempresa.serviceindex.utils.annotation.customAnnotation;

import com.miempresa.serviceindex.utils.annotation.handlerAnnotation.NotXSSValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NotXSSValidator.class)  // Especifica que la validación debe hacerse con la clase XSSValidator
@Target({ ElementType.FIELD, ElementType.PARAMETER })  // Se puede usar en campos (atributos) o en parámetros de métodos
@Retention(RetentionPolicy.RUNTIME)  // La anotación estará disponible en tiempo de ejecución
public @interface NotXSS {
    String message() default "XSS content is not allowed";  // Mensaje predeterminado que aparecerá si la validación falla
    Class<?>[] groups() default {};  // Se pueden agrupar validaciones, pero en este caso no es necesario
    Class<? extends Payload>[] payload() default {};  // Se pueden agregar datos adicionales, pero no se utiliza en este ejemplo
}
