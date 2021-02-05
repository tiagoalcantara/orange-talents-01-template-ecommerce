package br.com.zup.mercadolivre.pergunta;

import br.com.zup.mercadolivre.compartilhado.email.IMailer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class PerguntaEmailService {

    @Autowired
    private IMailer mailer;

    public void enviarPergunta(Pergunta pergunta, String uri) {
        String body = String.format("<html>\nOlá,\nVocê recebeu uma pergunta no seu produto \"%s\".\nVocê pode " +
                                            "visualizar clicando <a href=\"%s\">aqui</a>.\n</html>",
                                    pergunta.getProduto().getNome(),
                                    uri);

        mailer.send(body, "Nova pergunta", "Tiago", "tiago@nossomercadolivre.com", "donodoproduto@gmail.com");
    }
}
