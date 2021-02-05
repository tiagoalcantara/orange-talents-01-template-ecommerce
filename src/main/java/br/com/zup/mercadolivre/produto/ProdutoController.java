package br.com.zup.mercadolivre.produto;

import br.com.zup.mercadolivre.auth.UsuarioLogado;
import br.com.zup.mercadolivre.compartilhado.arquivos.IUploadDeImagens;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private IUploadDeImagens upload;

    @InitBinder(value = "CadastrarProdutoRequest")
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(new CategoriasComNomeDuplicadoValidator());
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> cadastrar(@RequestBody @Valid CadastrarProdutoRequest request,
                                       @AuthenticationPrincipal UsuarioLogado usuarioLogado) {
        Produto produto = request.toEntity(manager, usuarioLogado.get());

        manager.persist(produto);

        return ResponseEntity.ok()
                             .build();
    }

    @PostMapping("/{id}/adicionar-imagens")
    @Transactional
    public ResponseEntity<?> adicionarImagens(@Valid AdicionarImagensRequest request,
                                              @AuthenticationPrincipal UsuarioLogado usuarioLogado,
                                              @PathVariable Long id) {

        Produto produto = manager.find(Produto.class, id);

        if (produto == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "O produto informado não existe.");
        }

        if (!usuarioLogado.get().isDonoDoProduto(produto)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não tem autorização para esse produto.");
        }

        List<String> urls = request.getImagens()
                                   .stream()
                                   .map(imagem -> {
                                       try {
                                           return upload.salvar(imagem.getBytes(),
                                                                imagem.getOriginalFilename());
                                       } catch (IOException e) {
                                          throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não foi possível" +
                                                  " ler a imagem.");
                                       }
                                   })
                                   .collect(Collectors.toList());

        produto.adicionarImagens(urls);

        return ResponseEntity.ok().build();
    }
}
