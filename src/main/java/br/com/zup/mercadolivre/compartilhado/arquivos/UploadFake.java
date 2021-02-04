package br.com.zup.mercadolivre.compartilhado.arquivos;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UploadFake implements IUploadDeImagens{
    @Override
    public String salvar(byte[] imagem, String nomeDoArquivo) {
        String nomeGerado = String.format("%s-%s", UUID.randomUUID(), nomeDoArquivo);

        return String.format("http://sitelegitimodeimagens.io/%s", nomeGerado);
    }
}
