package br.com.zup.mercadolivre.config.handlers;

public class ErroDeCampoResponse {
    private String campo;
    private String mensagem;

    public ErroDeCampoResponse(String campo, String mensagem) {
        this.campo = campo;
        this.mensagem = mensagem;
    }

    public String getCampo() {
        return campo;
    }

    public String getMensagem() {
        return mensagem;
    }
}
