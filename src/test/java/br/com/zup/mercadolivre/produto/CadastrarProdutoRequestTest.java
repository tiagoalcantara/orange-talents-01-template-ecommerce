package br.com.zup.mercadolivre.produto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

class CadastrarProdutoRequestTest {

    @ParameterizedTest
    @MethodSource("deveEncontrarCaracteristicasComOMesmoNomeGerador")
    void deveEncontrarCaracteristicasComOMesmoNome(int esperado, List<CaracteristicaProduto> caracteristicas) {
        CadastrarProdutoRequest request = new CadastrarProdutoRequest("Nome", BigDecimal.TEN, 10, caracteristicas,
                                                                      "descrição", 1L);
        Assertions.assertEquals(esperado,
                                request.checarNomesIguais()
                                       .size());
    }

    static Stream<Arguments> deveEncontrarCaracteristicasComOMesmoNomeGerador() {
        return Stream.of(
                Arguments.of(0, List.of()),
                Arguments.of(0, List.of(new CaracteristicaProduto("nome", "descrição"))),
                Arguments.of(0, List.of(
                        new CaracteristicaProduto("nome", "descrição"),
                        new CaracteristicaProduto("nome 2", "descrição")
                                       )),
                Arguments.of(1, List.of(
                        new CaracteristicaProduto("nome", "descrição"),
                        new CaracteristicaProduto("nome", "descrição")
                                       ))
                        );
    }
}