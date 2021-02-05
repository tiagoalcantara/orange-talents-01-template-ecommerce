package br.com.zup.mercadolivre.compartilhado.email;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public interface IMailer {
    void send(@NotBlank String body, @NotBlank String subject, @NotBlank String nameFrom, @NotEmpty @Email String from,
              @NotEmpty @Email String to);
}
