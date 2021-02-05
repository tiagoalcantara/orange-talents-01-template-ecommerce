package br.com.zup.mercadolivre.opiniao;

import java.util.List;
import java.util.stream.Collectors;

public class DetalharOpiniaoResponse {
    private Long id;
    private String autor;
    private String titulo;
    private String descricao;
    private Integer nota;

    public DetalharOpiniaoResponse(Opiniao opiniao) {
        this.id = opiniao.getId();
        this.autor = opiniao.getAutor().getEmail();
        this.titulo = opiniao.getTitulo();
        this.descricao = opiniao.getDescricao();
        this.nota = opiniao.getNota();
    }

    public Long getId() {
        return id;
    }

    public String getAutor() {
        return autor;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public Integer getNota() {
        return nota;
    }

    public static List<DetalharOpiniaoResponse> converter(List<Opiniao> lista){
        return lista.stream().map(DetalharOpiniaoResponse::new).collect(Collectors.toList());
    }

}
