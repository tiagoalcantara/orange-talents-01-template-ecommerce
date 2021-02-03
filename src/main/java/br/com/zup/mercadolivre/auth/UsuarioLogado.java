package br.com.zup.mercadolivre.auth;

import br.com.zup.mercadolivre.usuario.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;

public class UsuarioLogado implements UserDetails {
    private Usuario usuario;
    private User springUserDetails;

    public UsuarioLogado(@NotNull @Valid Usuario usuario) {
        this.usuario = usuario;
        springUserDetails = new User(usuario.getEmail(), usuario.getSenha(), usuario.getPerfis());
    }

    public Usuario get() {
        return usuario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return springUserDetails.getAuthorities();
    }

    @Override
    public String getPassword() {
        return springUserDetails.getPassword();
    }

    @Override
    public String getUsername() {
        return springUserDetails.getUsername();
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
