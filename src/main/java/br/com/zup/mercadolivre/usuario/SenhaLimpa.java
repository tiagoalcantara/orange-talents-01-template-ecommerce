package br.com.zup.mercadolivre.usuario;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SenhaLimpa {
    @NotBlank
    @Size(min = 6)
    private String senhaLimpa;

    public SenhaLimpa(String senhaLimpa) {
        Assert.notNull(senhaLimpa, "A senha não pode ser nula.");
        Assert.isTrue(senhaLimpa.length() >= 6, "A senha deve ter 6 ou mais caractéres.");

        this.senhaLimpa = senhaLimpa;
    }

    public String hash() {
        return new BCryptPasswordEncoder().encode(senhaLimpa);
    }
}
