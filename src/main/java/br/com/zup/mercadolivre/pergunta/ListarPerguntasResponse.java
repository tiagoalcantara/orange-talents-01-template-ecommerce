package br.com.zup.mercadolivre.pergunta;

import java.util.List;
import java.util.stream.Collectors;

public class ListarPerguntasResponse {
    private Long id;
    private String titulo;

    public ListarPerguntasResponse(Pergunta pergunta) {
        this.titulo = pergunta.getTitulo();
        this.id = pergunta.getId();
    }

    public String getTitulo() {
        return titulo;
    }

    public Long getId() {
        return id;
    }

    public static List<ListarPerguntasResponse> converter(List<Pergunta> perguntas) {
        return perguntas.stream().map(ListarPerguntasResponse::new).collect(Collectors.toList());
    }
}
