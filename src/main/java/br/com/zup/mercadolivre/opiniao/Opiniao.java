package br.com.zup.mercadolivre.opiniao;

import br.com.zup.mercadolivre.produto.Produto;
import br.com.zup.mercadolivre.usuario.Usuario;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
public class Opiniao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Min(1)
    @Max(5)
    @Column(nullable = false)
    private Integer nota;

    @NotBlank
    @Column(nullable = false)
    private String titulo;

    @NotBlank
    @Column(nullable = false)
    private String descricao;

    @NotNull
    @ManyToOne
    private Produto produto;

    @NotNull
    @ManyToOne
    private Usuario autor;

    public Opiniao(@NotNull @Min(1) @Max(5) Integer nota,
                   @NotBlank String titulo,
                   @NotBlank String descricao,
                   @NotNull Produto produto,
                   @NotNull Usuario autor) {
        this.nota = nota;
        this.titulo = titulo;
        this.descricao = descricao;
        this.produto = produto;
        this.autor = autor;
    }

    public Produto getProduto() {
        return this.produto;
    }
}
