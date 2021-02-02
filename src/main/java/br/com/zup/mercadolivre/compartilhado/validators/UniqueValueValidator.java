package br.com.zup.mercadolivre.compartilhado.validators;

import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class UniqueValueValidator implements ConstraintValidator<UniqueValue, Object> {
    private String domainAttribute;
    private Class<?> aClass;

    @PersistenceContext
    private EntityManager manager;

    @Override
    public void initialize(UniqueValue constraintAnnotation) {
        this.domainAttribute = constraintAnnotation.fieldName();
        this.aClass = constraintAnnotation.domainClass();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        Query query = manager.createQuery("SELECT 1 FROM " + aClass.getName() + " WHERE " + domainAttribute + " = " +
                                                  ":value");
        query.setParameter("value", o);
        List<?> resultado = query.getResultList();

        Assert.state(resultado.size() <= 1, "Foram encontrados dois ou mais registros com um valor que deveria ser " +
                "único.");

        return resultado.isEmpty();
    }
}
