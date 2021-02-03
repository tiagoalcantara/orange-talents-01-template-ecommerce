package br.com.zup.mercadolivre.usuario;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime dataCriacao;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Perfil> perfis = new ArrayList<>();

    @Deprecated
    public Usuario(){}

    public Usuario(@NotEmpty @Email String email,
                   @NotNull @Valid SenhaLimpa senhaLimpa) {
        Assert.hasLength(email, "O e-mail não pode ser vazio.");
        Assert.notNull(senhaLimpa, "A senha limpa não pode ser nula");

        this.email = email;
        this.senha = senhaLimpa.hash();
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public List<Perfil> getPerfis() {
        return perfis;
    }
}
