package com.banco.basico.simulador.carteira.domain;

import com.banco.basico.simulador.carteira.application.ServiceCarteira;
import com.banco.basico.simulador.carteira.infrastructure.persistence.RepositoryCarteira;
import com.banco.basico.simulador.usuario.domain.Usuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class CarteiraTest {

    @Mock
    private RepositoryCarteira repositoryCarteira;

    @InjectMocks
    private ServiceCarteira serviceCarteira;

    @Captor
    private ArgumentCaptor<Carteira> carteiraArgumentCaptor;

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
    public void deveCadastrarCarteira() {
        Usuario usuario = new Usuario();
        usuario.setId(UUID.randomUUID());

        when(repositoryCarteira.existsByUsuario_Id(usuario.getId())).thenReturn(false);

        serviceCarteira.cadastrarCarteira(usuario);

        verify(repositoryCarteira, times(1)).save(carteiraArgumentCaptor.capture());

        Carteira carteiraCriadaPeloService = carteiraArgumentCaptor.getValue();

        assertNotNull(carteiraCriadaPeloService);
        assertEquals(usuario, carteiraCriadaPeloService.getUsuario());
        assertEquals(BigDecimal.valueOf(1000), carteiraCriadaPeloService.getSaldo());

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
