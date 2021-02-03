package br.com.zup.mercadolivre.auth;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class AutenticarRequest {
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String senha;

    public AutenticarRequest(@NotBlank @Email String email, @NotBlank String senha) {
        this.email = email;
        this.senha = senha;
    }

    public UsernamePasswordAuthenticationToken converter(){
        return new UsernamePasswordAuthenticationToken(email, senha);
    }
}
