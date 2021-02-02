package br.com.zup.mercadolivre.categoria;

import br.com.zup.mercadolivre.compartilhado.validators.ExistingId;
import br.com.zup.mercadolivre.compartilhado.validators.UniqueValue;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotBlank;

public class CadastrarCategoriaRequest {
    @NotBlank
    @UniqueValue(domainClass = Categoria.class, fieldName = "nome")
    private String nome;
    @ExistingId(domainClass = Categoria.class)
    private Long idCategoria;

    public CadastrarCategoriaRequest(@NotBlank String nome, Long idCategoria) {
        this.nome = nome;
        this.idCategoria = idCategoria;
    }

    public Categoria toEntity(EntityManager manager){
        Categoria categoriaMae = null;

        if(idCategoria != null) {
            categoriaMae = manager.find(Categoria.class, idCategoria);
        }

        return new Categoria(this.nome, categoriaMae);
    }

}
