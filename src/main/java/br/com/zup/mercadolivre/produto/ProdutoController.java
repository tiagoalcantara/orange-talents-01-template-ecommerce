package br.com.zup.mercadolivre.produto;

import br.com.zup.mercadolivre.auth.UsuarioLogado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

    @PersistenceContext
    private EntityManager manager;

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(new CategoriasComNomeDuplicadoValidator());
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> cadastrar(@RequestBody @Valid CadastrarProdutoRequest request,
                                       @AuthenticationPrincipal UsuarioLogado usuarioLogado){
        Produto produto = request.toEntity(manager, usuarioLogado.get());

        manager.persist(produto);

        return ResponseEntity.ok().build();
    }
}
