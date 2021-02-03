package br.com.zup.mercadolivre.produto;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.HashSet;

public class CategoriasComNomeDuplicadoValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return CadastrarProdutoRequest.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        if(errors.hasErrors()){
            return;
        }

        CadastrarProdutoRequest request = (CadastrarProdutoRequest) o;

        HashSet<String> nomesIguais = request.checarNomesIguais();

        nomesIguais.forEach(nomeIgual -> {
            errors.rejectValue("caracteristicas","CaracteristicaRepetida", "Caracteristica repetida (" + nomeIgual +
                    ").");
        });
    }
}
