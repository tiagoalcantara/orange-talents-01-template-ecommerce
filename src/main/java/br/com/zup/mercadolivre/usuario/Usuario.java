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
public class Usuario implements UserDetails {
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

    public Long getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.perfis;
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
