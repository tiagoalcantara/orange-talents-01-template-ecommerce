package br.com.zup.mercadolivre.usuario;

import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    @Email
    @Column(unique = true, nullable = false)
    private String email;
    @NotBlank
    @Size(min = 6)
    @Column(nullable = false)
    private String senha;
    @PastOrPresent
    @NotNull
    @Column(nullable = false)
    private LocalDateTime dataCriacao;

    public Usuario(@NotEmpty @Email String email,
                   @NotNull @Valid SenhaLimpa senhaLimpa) {
        Assert.hasLength(email, "O e-mail não pode ser vazio.");
        Assert.notNull(senhaLimpa, "A senha limpa não pode ser nula");

        this.email = email;
        this.senha = senhaLimpa.hash();
        this.dataCriacao = LocalDateTime.now();
    }
}
