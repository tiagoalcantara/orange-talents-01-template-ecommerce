package br.com.zup.mercadolivre.compartilhado.email;

import org.springframework.stereotype.Component;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Component
public class FakeMailer implements IMailer{
    @Override
    public void send(@NotBlank String body,
                     @NotBlank String subject,
                     @NotBlank String nameFrom,
                     @NotEmpty @Email String from,
                     @NotEmpty @Email String to) {

        String email = String.format("De: %s <%s>\nPara: %s\nAssunto: %s\n\n %s", nameFrom, from, to, subject, body);
        System.out.println(email);
    }
}
