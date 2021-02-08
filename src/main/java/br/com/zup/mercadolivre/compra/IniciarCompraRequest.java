package br.com.zup.mercadolivre.compra;

import br.com.zup.mercadolivre.produto.Produto;
import br.com.zup.mercadolivre.usuario.Usuario;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class IniciarCompraRequest {
    @NotNull
    @Positive
    private Integer quantidade;
    @NotNull
    private Gateway gateway;

    public IniciarCompraRequest(Integer quantidade, Gateway gateway) {
        this.quantidade = quantidade;
        this.gateway = gateway;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public Compra toEntity(Produto produto, Usuario comprador){
        return new Compra(produto, quantidade, comprador, gateway);
    }
}
