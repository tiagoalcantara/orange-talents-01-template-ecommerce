package br.com.zup.mercadolivre.compra;

import java.net.URI;

public enum Gateway {
    PAYPAL{
        @Override
        public URI getURI(Long id, String uriRetorno) {
            URI uri = URI.create(String.format("http://pagseguro.com?returnId=%s&redirectUrl=%s", id, uriRetorno));
            return uri;
        }
    },
    PAGSEGURO {
        @Override
        public URI getURI(Long id, String uriRetorno) {
            URI uri = URI.create(String.format("http://pagseguro.com?returnId=%s&redirectUrl=%s", id, uriRetorno));
            return uri;
        }
    };

    public abstract URI getURI(Long id, String uriRetorno);
}
