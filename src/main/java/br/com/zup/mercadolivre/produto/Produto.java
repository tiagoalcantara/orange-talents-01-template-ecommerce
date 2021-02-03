package br.com.zup.mercadolivre.produto;

import br.com.zup.mercadolivre.categoria.Categoria;
import br.com.zup.mercadolivre.usuario.Usuario;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Column(nullable = false)
    private String nome;
    @NotNull
    @Positive
    @Column(nullable = false)
    private BigDecimal valor;
    @NotNull
    @Positive
    @Column(nullable = false)
    private Integer quantidade;
    @Size(min = 3)
    @ElementCollection
    @CollectionTable(uniqueConstraints = {
            @UniqueConstraint(columnNames = {"produto_id", "nome"})
    })
    private List<CaracteristicaProduto> caracteristicas;
    @NotBlank
    @Size(max = 1000)
    @Column(nullable = false)
    private String descricao;
    @NotNull
    @Valid
    @ManyToOne
    private Categoria categoria;
    @CreationTimestamp
    private LocalDateTime dataCadastro;
    @NotNull
    @Valid
    @ManyToOne
    private Usuario dono;

    public Produto(@NotBlank String nome,
                   @NotNull @Positive BigDecimal valor,
                   @NotNull @Positive Integer quantidade,
                   @Size(min = 3) @Valid List<CaracteristicaProduto> caracteristicas,
                   @NotBlank @Size(max = 1000) String descricao,
                   @NotNull @Valid Categoria categoria,
                   @NotNull @Valid Usuario dono) {

        this.nome = nome;
        this.valor = valor;
        this.quantidade = quantidade;
        this.caracteristicas = caracteristicas;
        this.descricao = descricao;
        this.categoria = categoria;
        this.dono = dono;

        Assert.isTrue(this.caracteristicas.size() >= 3, "Deve ter pelo menos 3 características.");
        Assert.hasLength(this.nome, "O nome é obrigatório.");
        Assert.hasLength(this.descricao, "A descrição é obrigatória.");
        Assert.isTrue(this.quantidade > 0, "A quantidade deve ser positiva.");
        Assert.isTrue(this.valor.compareTo(BigDecimal.ZERO) > 0, "A quantidade deve ser positiva.");
        Assert.notNull(dono, "Deve ter um dono.");
        Assert.notNull(categoria, "Deve ter uma categoria.");
    }
}
