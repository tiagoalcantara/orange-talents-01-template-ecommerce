package br.com.zup.mercadolivre.compartilhado.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ErroDeValidacaoHandler {

    @Autowired
    private MessageSource messageSource;

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErroDeValidacaoResponse handle(MethodArgumentNotValidException exception) {
        List<ObjectError> errosGlobais = exception.getBindingResult()
                                                  .getGlobalErrors();
        List<FieldError> errosDeCampo = exception.getBindingResult()
                                                 .getFieldErrors();

        return buildErroDeValidacaoResponse(errosGlobais, errosDeCampo);
    }

    private ErroDeValidacaoResponse buildErroDeValidacaoResponse(List<ObjectError> errosGlobais,
                                                                 List<FieldError> errosDeCampo) {
        ErroDeValidacaoResponse erros = new ErroDeValidacaoResponse();

        errosGlobais.forEach(erro -> erros.addErroGlobal(getMensagem(erro)));

        errosDeCampo.forEach(erro -> {
            String mensagem = getMensagem(erro);
            erros.addErroDeCampo(erro.getField(), mensagem);
        });

        return erros;
    }

    private String getMensagem(ObjectError erro) {
        return messageSource.getMessage(erro, LocaleContextHolder.getLocale());
    }
}
