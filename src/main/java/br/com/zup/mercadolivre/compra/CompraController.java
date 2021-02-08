package br.com.zup.mercadolivre.compra;

import br.com.zup.mercadolivre.auth.UsuarioLogado;
import br.com.zup.mercadolivre.produto.Produto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

@RestController
public class CompraController {

    @PersistenceContext
    private EntityManager manager;

    @PostMapping("/produto/{id}/iniciar-compra")
    @Transactional
    public ResponseEntity<?> cadastrar(@RequestBody @Valid IniciarCompraRequest request,
                                       @PathVariable("id") Long idProduto,
                                       @AuthenticationPrincipal UsuarioLogado usuarioLogado) {
        Produto produto = manager.find(Produto.class, idProduto);

        if (produto == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "O produto informado não existe.");
        }

        if(request.getQuantidade() > produto.getQuantidade()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A quantidade informada excede o estoque.");
        }

        produto.abateEstoque(request.getQuantidade());

        Compra compra = request.toEntity(produto, usuarioLogado.get());
        manager.persist(compra);

        URI uriRetorno = URI.create("/fechar-compra");
        URI uri = compra.getPagamento().getURI(compra.getId(), uriRetorno.toString());
        return ResponseEntity.status(HttpStatus.FOUND).location(uri).build();
    }
}
