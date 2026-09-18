package com.banco.basico.simulador.autenticacao.infrastructure.ratelimit;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RateLimitService {
    private Map<UUID, Bucket> buckets = new ConcurrentHashMap<>();

    private static int CAPACIDADE = 10;
    private static int TOKENS_POR_MINUTO = 5;

    public boolean permitir(UUID idUsuario) {
        Bucket bucket = buckets.computeIfAbsent(idUsuario, id -> criarBucket());

        return bucket.tryConsume(1);
    }


    private Bucket criarBucket() {
        Bandwidth limite = Bandwidth.builder()
                .capacity(CAPACIDADE)
                .refillGreedy(TOKENS_POR_MINUTO, Duration.ofMinutes(1))
                .build();
                ;

        return Bucket.builder().addLimit(limite).build();
    }

}
