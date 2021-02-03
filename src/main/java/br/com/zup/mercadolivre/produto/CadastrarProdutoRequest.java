package br.com.zup.mercadolivre.produto;

import br.com.zup.mercadolivre.categoria.Categoria;
import br.com.zup.mercadolivre.compartilhado.validators.ExistingId;
import br.com.zup.mercadolivre.usuario.Usuario;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CadastrarProdutoRequest {
    @NotBlank
    private String nome;
    @NotNull
    @Positive
    private BigDecimal valor;
    @NotNull
    @Positive
    private Integer quantidade;
    @NotNull
    @Size(min = 3)
    @Valid
    private List<CaracteristicaProduto> caracteristicas = new ArrayList<>();
    @NotBlank
    @Size(max = 1000)
    private String descricao;
    @NotNull
    @ExistingId(domainClass = Categoria.class)
    private Long idCategoria;

    public CadastrarProdutoRequest(@NotBlank String nome,
                                   @NotNull @Positive BigDecimal valor,
                                   @NotNull @Positive Integer quantidade,
                                   @NotNull @Size(min = 3) List<CaracteristicaProduto> caracteristicas,
                                   @NotBlank @Size(max = 1000) String descricao,
                                   @NotNull Long idCategoria) {
        this.nome = nome;
        this.valor = valor;
        this.quantidade = quantidade;
        this.caracteristicas.addAll(caracteristicas);
        this.descricao = descricao;
        this.idCategoria = idCategoria;
    }

    public List<CaracteristicaProduto> getCaracteristicas() {
        return caracteristicas;
    }

    public Produto toEntity(EntityManager manager, Usuario usuario) {
        Categoria categoria = manager.find(Categoria.class, idCategoria);

        return new Produto(this.nome, this.valor, this.quantidade, this.caracteristicas, this.descricao, categoria,
                           usuario);
    }
}
