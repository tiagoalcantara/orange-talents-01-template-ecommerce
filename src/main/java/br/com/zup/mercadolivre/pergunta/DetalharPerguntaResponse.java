package br.com.zup.mercadolivre.pergunta;

import java.util.List;
import java.util.stream.Collectors;

public class DetalharPerguntaResponse {
    private Long id;
    private String titulo;

    public DetalharPerguntaResponse(Pergunta pergunta) {
        this.titulo = pergunta.getTitulo();
        this.id = pergunta.getId();
    }

    public String getTitulo() {
        return titulo;
    }

    public Long getId() {
        return id;
    }

    public static List<DetalharPerguntaResponse> converter(List<Pergunta> perguntas) {
        return perguntas.stream().map(DetalharPerguntaResponse::new).collect(Collectors.toList());
    }
}
