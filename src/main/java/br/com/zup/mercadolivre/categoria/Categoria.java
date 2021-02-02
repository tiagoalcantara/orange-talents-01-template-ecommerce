package br.com.zup.mercadolivre.categoria;

import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Column(nullable = false, unique = true)
    private String nome;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoriaMae;

    @OneToMany(mappedBy = "categoriaMae", fetch = FetchType.EAGER)
    private List<Categoria> categoriasFilhas;

    @Deprecated
    public Categoria(){}

    public Categoria(@NotBlank String nome, Categoria categoriaMae) {
        Assert.hasLength(nome, "O nome da categoria não pode ser nulo.");

        this.nome = nome;
        this.categoriaMae = categoriaMae;
    }
}
