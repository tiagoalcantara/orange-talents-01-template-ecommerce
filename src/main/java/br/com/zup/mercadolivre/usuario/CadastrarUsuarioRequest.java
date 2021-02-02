package br.com.zup.mercadolivre.usuario;

import br.com.zup.mercadolivre.compartilhado.validators.UniqueValue;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.*;

public class CadastrarUsuarioRequest {
    @Email
    @NotEmpty
    @UniqueValue(domainClass = Usuario.class, fieldName = "email")
    private String email;

    @NotBlank
    @Size(min = 6)
    private String senha;

    public CadastrarUsuarioRequest(@Email @NotEmpty String email,
                                   @NotBlank @Size(min = 6) String senha) {
        this.email = email;
        this.senha = senha;
    }

    public Usuario toEntity() {
        return new Usuario(email, new SenhaLimpa(senha));
    }
}
