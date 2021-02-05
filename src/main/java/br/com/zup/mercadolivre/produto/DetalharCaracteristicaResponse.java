package br.com.zup.mercadolivre.produto;

import java.util.List;
import java.util.stream.Collectors;

public class DetalharCaracteristicaResponse {
    private String nome;
    private String descricao;

    public DetalharCaracteristicaResponse(CaracteristicaProduto caracteristicaProduto) {
        this.nome = caracteristicaProduto.getNome();
        this.descricao = caracteristicaProduto.getDescricao();
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public static List<DetalharCaracteristicaResponse> converter(List<CaracteristicaProduto> lista) {
        return lista.stream().map(DetalharCaracteristicaResponse::new).collect(Collectors.toList());
    }
}
