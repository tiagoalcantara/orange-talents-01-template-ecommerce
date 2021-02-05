package br.com.zup.mercadolivre.pergunta;

import br.com.zup.mercadolivre.produto.Produto;
import br.com.zup.mercadolivre.usuario.Usuario;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;

@Entity
public class Pergunta {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String titulo;

    @PastOrPresent
    @CreationTimestamp
    private LocalDateTime dataCriacao;

    @NotNull
    @ManyToOne
    private Usuario autor;

    @NotNull
    @ManyToOne
    private Produto produto;

    public Pergunta(@NotBlank String titulo,
                    @NotNull Usuario autor,
                    @NotNull Produto produto) {
        this.titulo = titulo;
        this.autor = autor;
        this.produto = produto;
    }

    public Produto getProduto() {
        return this.produto;
    }
}
