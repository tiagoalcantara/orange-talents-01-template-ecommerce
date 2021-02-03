package br.com.zup.mercadolivre.produto;

import br.com.zup.mercadolivre.categoria.Categoria;
import br.com.zup.mercadolivre.usuario.SenhaLimpa;
import br.com.zup.mercadolivre.usuario.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ProdutoTest {

    @ParameterizedTest
    @MethodSource("deveTerNoMinimo3CaracteristicasGerador")
    void deveTerNoMinimo3Caracteristicas(List<CaracteristicaProduto> caracteristicas) throws Exception {
        Categoria categoria = new Categoria("categoria");
        Usuario dono = new Usuario("teste@email.com", new SenhaLimpa("123456"));

        new Produto("nome", BigDecimal.TEN, 10, caracteristicas, "descrição", categoria, dono);
    }

    static Stream<Arguments> deveTerNoMinimo3CaracteristicasGerador() {
        return Stream.of(
                Arguments.of(
                        List.of(new CaracteristicaProduto("nome", "descricao"),
                                new CaracteristicaProduto("nome2", "descricao"),
                                new CaracteristicaProduto("nome3", "descricao"),
                                new CaracteristicaProduto("nome4", "descricao"))
                            ),
                Arguments.of(
                        List.of(
                                new CaracteristicaProduto("nome", "descricao"),
                                new CaracteristicaProduto("nome2", "descricao"),
                                new CaracteristicaProduto("nome3", "descricao"))
                            )
                        );
    }

    @ParameterizedTest
    @MethodSource("naoDeveTerMenosDe3CaracteristicasGerador")
    void naoDeveTerMenosDe3Caracteristicas(List<CaracteristicaProduto> caracteristicas) throws Exception {
        Categoria categoria = new Categoria("categoria");
        Usuario dono = new Usuario("teste@email.com", new SenhaLimpa("123456"));

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Produto("nome", BigDecimal.TEN, 10, caracteristicas, "descrição", categoria, dono);
        });
    }

    static Stream<Arguments> naoDeveTerMenosDe3CaracteristicasGerador() {
        return Stream.of(
                Arguments.of(
                        List.of(new CaracteristicaProduto("nome", "descricao"))
                            ),
                Arguments.of(
                        List.of(
                                new CaracteristicaProduto("nome", "descricao"),
                                new CaracteristicaProduto("nome2", "descricao"))
                            ),
                Arguments.of(
                        List.of()
                            )
                        );
    }
}