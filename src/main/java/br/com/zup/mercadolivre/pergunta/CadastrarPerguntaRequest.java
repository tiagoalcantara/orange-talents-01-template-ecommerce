package br.com.zup.mercadolivre.pergunta;

import br.com.zup.mercadolivre.produto.Produto;
import br.com.zup.mercadolivre.usuario.Usuario;
import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.NotBlank;

public class CadastrarPerguntaRequest {

    @NotBlank
    private String titulo;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public CadastrarPerguntaRequest(@NotBlank String titulo) {
        this.titulo = titulo;
    }

    public Pergunta toEntity(Produto produto, Usuario usuario) {
        return new Pergunta(titulo, usuario, produto);
    }
}
