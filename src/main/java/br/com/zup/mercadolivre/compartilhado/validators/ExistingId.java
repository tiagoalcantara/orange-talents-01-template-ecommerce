package br.com.zup.mercadolivre.compartilhado.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = { ExistingIdValidator.class })
@Target({ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistingId {
    String message() default "Id inexistente.";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    String fieldName() default "id";

    Class<?> domainClass();
}
