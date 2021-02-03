package br.com.zup.mercadolivre.auth;

import br.com.zup.mercadolivre.auth.UsuarioLogado;
import br.com.zup.mercadolivre.usuario.Usuario;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Service
public class AutenticacaoService implements UserDetailsService {

    @PersistenceContext
    private EntityManager manager;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        TypedQuery<Usuario> query = manager.createQuery("SELECT u FROM Usuario u WHERE u.email = :email",
                                                        Usuario.class);
        query.setParameter("email", email);
        Usuario usuario = query.getSingleResult();

        if(usuario == null) {
            throw new UsernameNotFoundException("Email/senha incorretos.");
        }

        return new UsuarioLogado(usuario);
    }
}
