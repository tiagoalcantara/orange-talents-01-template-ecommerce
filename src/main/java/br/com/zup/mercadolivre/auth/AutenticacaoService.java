package br.com.zup.mercadolivre.auth;

import br.com.zup.mercadolivre.usuario.Usuario;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Service
public class AutenticacaoService implements UserDetailsService {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Query query = manager.createQuery("SELECT u FROM Usuario u WHERE u.email = :email",
                                          Usuario.class);
        query.setParameter("email", email);

        List<?> resultList = query.getResultList();

        if(resultList.isEmpty()) {
            throw new UsernameNotFoundException("Email/senha incorretos.");
        }

        Assert.state(resultList.size() <= 1, "Foram encontrados dois usuários com o mesmo e-mail, que deveria ser " +
                "único.");

        Usuario usuario = (Usuario) resultList.get(0);
        return new UsuarioLogado(usuario);
    }
}
