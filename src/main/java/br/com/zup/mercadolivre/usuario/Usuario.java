package br.com.zup.mercadolivre.usuario;

import javax.persistence.*;
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
                   @NotBlank @Size(min = 6) String senha) {
        this.email = email;
        this.senha = senha;
        this.dataCriacao = LocalDateTime.now();
    }
}
