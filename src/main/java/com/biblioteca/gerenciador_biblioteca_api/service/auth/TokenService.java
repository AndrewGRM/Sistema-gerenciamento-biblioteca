package com.biblioteca.gerenciador_biblioteca_api.service.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.biblioteca.gerenciador_biblioteca_api.model.Member;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    public String generateToken(Member member) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("minha-chave-secreta");

            return JWT.create()
                    .withIssuer("biblioteca-api")
                    .withSubject(member.getEmail())
                    .withExpiresAt(generateExpirationDate())
                    .sign(algorithm);

        } catch (Exception exception) {
            throw new RuntimeException("Erro ao gerar o token!", exception);
        }
    }

    private Instant generateExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

    public String validateToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256("minha-chave-secreta");
            return JWT.require(algorithm)
                    .withIssuer("biblioteca-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            return "";
        }
    }

}
