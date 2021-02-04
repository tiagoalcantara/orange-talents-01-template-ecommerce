package br.com.zup.mercadolivre.produto;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public class AdicionarImagensRequest {

    @NotNull
    @Size(min = 1)
    private List<MultipartFile> imagens = new ArrayList<>();

    public List<MultipartFile> getImagens() {
        return imagens;
    }

    public void setImagens(List<MultipartFile> imagens) {
        this.imagens = imagens;
    }
}
