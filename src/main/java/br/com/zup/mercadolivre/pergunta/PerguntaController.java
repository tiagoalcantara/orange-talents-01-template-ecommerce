package br.com.zup.mercadolivre.pergunta;

import br.com.zup.mercadolivre.auth.UsuarioLogado;
import br.com.zup.mercadolivre.produto.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@RestController
public class PerguntaController {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private PerguntaEmailService perguntaEmailService;

    @PostMapping("/produto/{id}/pergunta")
    @Transactional
    public ResponseEntity<List<DetalharPerguntaResponse>> cadastrar(@RequestBody @Valid CadastrarPerguntaRequest request,
                                                                    @AuthenticationPrincipal UsuarioLogado usuarioLogado,
                                                                    @PathVariable("id") Long produtoId,
                                                                    UriComponentsBuilder uriBuilder) {
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

        String uriProduto = uriBuilder.path("/produto/{id}").buildAndExpand(pergunta.getProduto().getId()).toUriString();
        perguntaEmailService.enviarPergunta(pergunta, uriProduto);

        TypedQuery<Pergunta> query = manager.createQuery("SELECT p FROM Pergunta p WHERE p.produto = :produto",
                                                         Pergunta.class);
        query.setParameter("produto", produto);

        List<Pergunta> perguntas = query.getResultList();

        return ResponseEntity.ok(DetalharPerguntaResponse.converter(perguntas));
    }
}
