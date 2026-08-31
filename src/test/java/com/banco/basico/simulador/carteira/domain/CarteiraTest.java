package com.banco.basico.simulador.carteira.domain;

import com.banco.basico.simulador.carteira.domain.Carteira;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CarteiraTest {
    @Test
    public void devesSacarQuandoHouverSaldo() {
        Carteira carteira = new Carteira();
        //Carteira já começa com 1000 de saldo, por isso so estamos tirando e não inserindo saldo
        carteira.sacar(new  BigDecimal("30.00"));

        assertEquals(
                0,
                carteira.getSaldo().compareTo(new BigDecimal("970.00")),
                "O saldo deveria ser 970, pois a carteira ja começa com 1000. 1000 - 30 = 970"
        );
    }

    @Test
    public void deveDepositarValorNaCarteira() {
        Carteira carteira = new Carteira();
        carteira.depositar(new  BigDecimal("30.00"));

        assertEquals(
                0,
                carteira.getSaldo().compareTo(new BigDecimal("1030.00"))
        );
    }

    @Test
    public void deveRetornarIllegalArgumentException() {
        Carteira carteira = new Carteira();

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> carteira.depositar(BigDecimal.valueOf(-30))
        );

        assertEquals(
                "O valor deve ser maior que zero",
                exception.getMessage()
        );
    }
}
