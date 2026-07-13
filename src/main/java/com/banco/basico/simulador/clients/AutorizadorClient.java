package com.banco.basico.simulador.clients;

import com.banco.basico.simulador.dto.DtoRespostaAutorizador;
import com.banco.basico.simulador.exceptions.ServicoIndisponivelException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class AutorizadorClient {
    private static final String URL_AUTORIZADOR = "http://localhost:8080/mock/authorize";

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
