package com.banco.basico.simulador.integracao.autorizador.client;

import com.banco.basico.simulador.integracao.autorizador.client.dto.DtoRespostaAutorizador;
import com.banco.basico.simulador.integracao.autorizador.client.exception.ServicoIndisponivelException;
import com.banco.basico.simulador.transferencia.application.AutorizadorTransferencia;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class AutorizadorClient implements AutorizadorTransferencia {

    @Getter
    @Value("${URL_AUTORIZADOR}")
    private String URL_AUTORIZADOR;

    private final RestTemplate restTemplate;


    public boolean autorizar() {
        try {
            ResponseEntity<DtoRespostaAutorizador> resposta = restTemplate.getForEntity(
                    URL_AUTORIZADOR,
                    DtoRespostaAutorizador.class
            );

            if (resposta.getBody() == null) {
                throw new ServicoIndisponivelException("Autorizador não retornou resposta");
            }

            return resposta.getBody().data().authorization();

        } catch (ResourceAccessException e) {
            throw new ServicoIndisponivelException("Serviço autorizador indisponível");
        }
    }

}
