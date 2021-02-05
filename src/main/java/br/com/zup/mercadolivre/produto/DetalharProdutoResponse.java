package br.com.zup.mercadolivre.produto;

import br.com.zup.mercadolivre.opiniao.DetalharOpiniaoResponse;
import br.com.zup.mercadolivre.pergunta.DetalharPerguntaResponse;

import java.math.BigDecimal;
import java.util.List;

public class DetalharProdutoResponse {
    private List<String> imagens;
    private String nome;
    private BigDecimal valor;
    private String descricao;
    private Double mediaAvaliação;
    private Integer totalAvaliacoes;
    private List<DetalharCaracteristicaResponse> caracteristicas;
    private List<DetalharOpiniaoResponse> opinioes;
    private List<DetalharPerguntaResponse> perguntas;

    public DetalharProdutoResponse(Produto produto) {
        this.imagens = produto.getImagens();
        this.nome = produto.getNome();
        this.valor = produto.getValor();
        this.descricao = produto.getDescricao();
        this.mediaAvaliação = produto.getMediaDeAvaliacoes();
        this.totalAvaliacoes = produto.getTotalDeAvaliacoes();
        this.caracteristicas = DetalharCaracteristicaResponse.converter(produto.getCaracteristicas());
        this.opinioes = DetalharOpiniaoResponse.converter(produto.getOpinioes());
        this.perguntas = DetalharPerguntaResponse.converter(produto.getPerguntas());
    }

    public List<String> getImagens() {
        return imagens;
    }

    public String getNome() {
        return nome;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public Double getMediaAvaliação() {
        return mediaAvaliação;
    }

    public Integer getTotalAvaliacoes() {
        return totalAvaliacoes;
    }

    public List<DetalharCaracteristicaResponse> getCaracteristicas() {
        return caracteristicas;
    }

    public List<DetalharOpiniaoResponse> getOpinioes() {
        return opinioes;
    }

    public List<DetalharPerguntaResponse> getPerguntas() {
        return perguntas;
    }
}
