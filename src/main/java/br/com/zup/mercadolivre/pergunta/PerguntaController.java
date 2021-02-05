package br.com.zup.mercadolivre.pergunta;

import br.com.zup.mercadolivre.auth.UsuarioLogado;
import br.com.zup.mercadolivre.opiniao.Opiniao;
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

@RestController
public class PerguntaController {

    @PersistenceContext
    private EntityManager manager;

    @PostMapping("/produto/{id}/pergunta")
    @Transactional
    public ResponseEntity<?> cadastrar(@RequestBody @Valid CadastrarPerguntaRequest request,
                                       @AuthenticationPrincipal UsuarioLogado usuarioLogado,
                                       @PathVariable("id") Long produtoId) {
        Produto produto = manager.find(Produto.class, produtoId);

        if (produto == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "O produto informado não existe.");
        }

        Pergunta pergunta = request.toEntity(produto, usuarioLogado.get());

        if (usuarioLogado.get()
                         .isDonoDoProduto(pergunta.getProduto())) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Você não pode perguntar no seu " +
                    "produto.");
        }

        manager.persist(pergunta);

        return ResponseEntity.ok().build();
    }
}
