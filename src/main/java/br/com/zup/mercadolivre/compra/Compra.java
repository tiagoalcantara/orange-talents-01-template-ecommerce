package br.com.zup.mercadolivre.compra;

import br.com.zup.mercadolivre.produto.Produto;
import br.com.zup.mercadolivre.usuario.Usuario;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
public class Compra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @ManyToOne
    private Produto produto;
    @NotNull
    @Positive
    private Integer quantidade;
    @NotNull
    @ManyToOne
    private Usuario comprador;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Gateway pagamento;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;


    public Compra(@NotNull Produto produto,
                  @NotNull @Positive Integer quantidade,
                  @NotNull Usuario comprador,
                  @NotNull Gateway pagamento) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.comprador = comprador;
        this.pagamento = pagamento;
        this.status = Status.INICIADA;
    }

    public Long getId() {
        return id;
    }

    public Gateway getPagamento() {
        return pagamento;
    }
}
