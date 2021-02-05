package br.com.zup.mercadolivre.opiniao;

import br.com.zup.mercadolivre.compartilhado.validators.ExistingId;
import br.com.zup.mercadolivre.produto.Produto;
import br.com.zup.mercadolivre.usuario.Usuario;

import javax.persistence.EntityManager;
import javax.validation.constraints.*;

public class CadastrarOpiniaoRequest {

    @NotNull
    @Min(1)
    @Max(5)
    private Integer nota;

    @NotBlank
    private String titulo;

    @NotBlank
    private String descricao;

    public CadastrarOpiniaoRequest(@NotNull @Min(1) @Max(5) Integer nota,
                                   @NotBlank String titulo,
                                   @NotBlank String descricao) {
        this.nota = nota;
        this.titulo = titulo;
        this.descricao = descricao;
    }

    public Opiniao toEntity(Produto produto, Usuario autor) {
        return new Opiniao(this.nota, this.titulo, this.descricao, produto, autor);
    }
}
