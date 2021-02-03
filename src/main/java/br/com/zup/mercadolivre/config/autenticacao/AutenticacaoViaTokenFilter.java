package br.com.zup.mercadolivre.config.autenticacao;

import br.com.zup.mercadolivre.usuario.Usuario;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.persistence.EntityManager;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AutenticacaoViaTokenFilter extends OncePerRequestFilter {

    private TokenService tokenService;
    private EntityManager manager;

    public AutenticacaoViaTokenFilter(TokenService tokenService, EntityManager manager) {
        this.tokenService = tokenService;
        this.manager = manager;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = recuperarToken(httpServletRequest);
        boolean valido = tokenService.valido(token);

        if (valido) {
            autenticarCliente(token);
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);

    }

    private void autenticarCliente(String token) {
        Long idUsuario = tokenService.getIdUsuario(token);
        Usuario usuario = manager.find(Usuario.class, idUsuario);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(usuario, null,
                                                                                                     usuario.getAuthorities());
        SecurityContextHolder.getContext()
                             .setAuthentication(authentication);
    }

    private String recuperarToken(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("Authorization");
        if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
            return null;
        }

        return token.split(" ")[1];
    }
}
