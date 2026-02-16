package org.example.Services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.example.Model.Administrador;

public class TokenService {

    private static final String SEGREDO = System.getenv("JWT_SECRET") != null
            ? System.getenv("JWT_SECRET")
            : "Chave_Provisoria_Trabalho";
    private static final Algorithm ALGORITMO = Algorithm.HMAC256(SEGREDO);

    // GERA TOKEN
    public static String gerarToken(Administrador administrador) {
        return JWT.create()
                .withSubject(administrador.getEmail())
                .withClaim("id", administrador.getId())
                .sign(ALGORITMO);
    }

    // VALIDA TOKEN
    public static String validarToken(String token) {
        try {
            String tk = token.replace("Bearer ", "");

            DecodedJWT jwt = JWT.require(ALGORITMO)
                    .build()
                    .verify(tk);

            return jwt.getSubject();
        } catch (JWTVerificationException e) {
            return null;
        }
    }
}