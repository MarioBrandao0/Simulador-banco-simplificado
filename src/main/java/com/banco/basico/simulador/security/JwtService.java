package com.banco.basico.simulador.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    private SecretKey pegarSecretKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String gerarToken(String email, UUID id, String nome) {
        return Jwts.builder()
                .subject(email)
                .claim("id", id.toString())
                .claim("nome", nome)
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(pegarSecretKey())
                .compact();
    }

    public String extrairEmail(String token) {
        return Jwts.parser()
                .verifyWith(pegarSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean VerificarToken(String token) {
        String email = extrairEmail(token);

        Date expiracao = Jwts.parser()
                .verifyWith(pegarSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration();
        
        return email != null && expiracao.after(new Date());

    }

}
