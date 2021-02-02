package br.com.zup.mercadolivre.compartilhado.handlers;

import java.util.ArrayList;
import java.util.List;

public class ErroDeValidacaoResponse {
    private List<String> global = new ArrayList<>();
    private List<ErroDeCampoResponse> campos = new ArrayList<>();
    private Integer quantidade = 0;

    public void addErroGlobal(String mensagem) {
        global.add(mensagem);
        quantidade = this.quantidade + 1;
    }

    public void addErroDeCampo(String campo, String mensagem) {
        campos.add(new ErroDeCampoResponse(campo, mensagem));
        quantidade = this.quantidade + 1;
    }

    public List<String> getGlobal() {
        return global;
    }

    public List<ErroDeCampoResponse> getCampos() {
        return campos;
    }

    public Integer getQuantidade() {
        return quantidade;
    }
}
