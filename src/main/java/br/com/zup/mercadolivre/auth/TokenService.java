package br.com.zup.mercadolivre.auth;

import br.com.zup.mercadolivre.auth.UsuarioLogado;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    @Value("${mercadolivre.jwt.tempoParaExpirar}")
    private String tempoParaExpirar;

    @Value("${mercadolivre.jwt.segredo}")
    private String segredo;

    public String gerarToken(Authentication authentication) {
        UsuarioLogado logado = (UsuarioLogado) authentication.getPrincipal();
        Date hoje = new Date();
        Date expiracao = new Date(hoje.getTime() + Long.parseLong(tempoParaExpirar));

        return Jwts.builder().setIssuer("Desafio mercado livre")
                .setSubject(logado.getUsername())
                .setIssuedAt(hoje)
                .setExpiration(expiracao)
                .signWith(SignatureAlgorithm.HS256, segredo)
                .compact();
    }

    public boolean valido(String token){
        try {
            Jwts.parser().setSigningKey(this.segredo).parseClaimsJws(token);
            return true;
        } catch(Exception e) {
            return false;
        }
    }

    public String getUsername(String token) {
        Claims claims = Jwts.parser().setSigningKey(this.segredo).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}
