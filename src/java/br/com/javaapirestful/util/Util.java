package br.com.javaapirestful.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.Calendar;

public class Util {

    private static final int EXPIRES_AT = 1440; // MINUTOS
    private static final String SECRET = "API_REST_JAVA";

    public static String generateToken() throws JWTCreationException {

        Algorithm algorithm = Algorithm.HMAC256(SECRET);

        Calendar expiresAt = Calendar.getInstance();
        expiresAt.add(Calendar.MINUTE, EXPIRES_AT);

        String token = JWT.create()
                .withIssuer("auth0")
                .withExpiresAt(expiresAt.getTime())
                .sign(algorithm);

        return token;

    }

    public static boolean isValidToken(String token) throws TokenExpiredException, JWTVerificationException {

        Algorithm algorithm = Algorithm.HMAC256(SECRET);

        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("auth0")
                .acceptExpiresAt(EXPIRES_AT)
                .build();

        DecodedJWT jwt = verifier.verify(token);

        return true;

    }

}
