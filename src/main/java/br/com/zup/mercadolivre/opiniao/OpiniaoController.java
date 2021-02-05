package br.com.zup.mercadolivre.opiniao;

import br.com.zup.mercadolivre.auth.UsuarioLogado;
import br.com.zup.mercadolivre.produto.Produto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
public class OpiniaoController {

    @PersistenceContext
    private EntityManager manager;

    @PostMapping("/produto/{id}/opiniao")
    @Transactional
    public ResponseEntity<?> cadastrar(@RequestBody @Valid CadastrarOpiniaoRequest request,
                                       @PathVariable("id") Long produtoId,
                                       @AuthenticationPrincipal UsuarioLogado usuarioLogado) {

        Produto produto = manager.find(Produto.class, produtoId);

        if (produto == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "O produto informado não existe.");
        }

        Opiniao opiniao = request.toEntity(produto, usuarioLogado.get());

        if (usuarioLogado.get()
                         .isDonoDoProduto(opiniao.getProduto())) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Você não pode opinar no seu produto.");
        }

        manager.persist(opiniao);

        return ResponseEntity.ok()
                             .build();
    }
}
