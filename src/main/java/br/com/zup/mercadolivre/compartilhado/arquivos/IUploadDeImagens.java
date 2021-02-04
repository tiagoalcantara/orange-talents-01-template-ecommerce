package br.com.zup.mercadolivre.compartilhado.arquivos;

public interface IUploadDeImagens {
    String salvar(byte[] imagem, String nomeDoArquivo);
}
