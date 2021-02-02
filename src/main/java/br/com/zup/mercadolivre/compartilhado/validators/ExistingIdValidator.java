package br.com.zup.mercadolivre.compartilhado.validators;

import org.springframework.util.Assert;

import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class ExistingIdValidator implements ConstraintValidator<ExistingId, Object> {
    private String domainAttribute;
    private Class<?> aClass;

    @PersistenceContext
    private EntityManager manager;

    @Override
    public void initialize(ExistingId constraintAnnotation) {
        domainAttribute = constraintAnnotation.fieldName();
        aClass = constraintAnnotation.domainClass();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        if(o == null){
            return true;
        }

        Query query = manager.createQuery("select 1 from " + aClass.getName() + " o where " + domainAttribute + " = " +
                                                  ":value");
        query.setParameter("value", o);

        List<?> list = query.getResultList();

        Assert.state(list.size() <= 1, "Foi encontrado mais de um registro com o mesmo identificador.");

        return !list.isEmpty();
    }
}
