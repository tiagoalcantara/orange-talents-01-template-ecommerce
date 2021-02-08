package br.com.zup.mercadolivre.produto;

import br.com.zup.mercadolivre.categoria.Categoria;
import br.com.zup.mercadolivre.opiniao.Opiniao;
import br.com.zup.mercadolivre.pergunta.Pergunta;
import br.com.zup.mercadolivre.usuario.Usuario;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

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
    @PositiveOrZero
    @Column(nullable = false)
    private Integer quantidade;
    @Size(min = 3)
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(uniqueConstraints = {
            @UniqueConstraint(columnNames = {"produto_id", "nome"})
    })
    private List<CaracteristicaProduto> caracteristicas;
    @ElementCollection(fetch = FetchType.LAZY)
    @Column(name = "url")
    private List<String> imagens;
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

    @OneToMany(mappedBy = "produto")
    List<Opiniao> opinioes;

    @OneToMany(mappedBy = "produto")
    List<Pergunta> perguntas;

    @Deprecated
    public Produto(){}

    public Produto(@NotBlank String nome,
                   @NotNull @Positive BigDecimal valor,
                   @NotNull @PositiveOrZero Integer quantidade,
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
        this.imagens = new ArrayList<>();

        Assert.isTrue(this.caracteristicas.size() >= 3, "Deve ter pelo menos 3 características.");
        Assert.hasLength(this.nome, "O nome é obrigatório.");
        Assert.hasLength(this.descricao, "A descrição é obrigatória.");
        Assert.isTrue(this.quantidade > 0, "A quantidade deve ser positiva.");
        Assert.isTrue(this.valor.compareTo(BigDecimal.ZERO) > 0, "A quantidade deve ser positiva.");
        Assert.notNull(dono, "Deve ter um dono.");
        Assert.notNull(categoria, "Deve ter uma categoria.");
    }

    public Usuario getDono() {
        return dono;
    }

    public void adicionarImagens(List<String> imagens){
        this.imagens.addAll(imagens);
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public List<String> getImagens() {
        return imagens;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public List<CaracteristicaProduto> getCaracteristicas() {
        return caracteristicas;
    }

    public List<Pergunta> getPerguntas() {
        return perguntas;
    }

    public List<Opiniao> getOpinioes() {
        return opinioes;
    }

    public Double getMediaDeAvaliacoes(){
        OptionalDouble media = opinioes.stream().mapToDouble(opiniao -> opiniao.getNota().doubleValue()).average();

        return media.orElse(0);
    }

    public Integer getTotalDeAvaliacoes() {
        return opinioes.size();
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void abateEstoque(Integer quantidade) {
        this.quantidade = this.quantidade - quantidade;
    }
}
