package dev.sirosh.bonus_service.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import dev.sirosh.bonus_service.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class JwtManager {
    private final String jwtIssuer;
    private final JWTVerifier verifier;
    private final Algorithm algorithm;

    public JwtManager(@Value("{jwt.secret:'super_secret'}") String jwtSecrete,
                      @Value("{jwt.issuer:'bonus_program'}") String jwtIssuer) {
        this.jwtIssuer = jwtIssuer;
        this.algorithm = Algorithm.HMAC256(jwtSecrete);
        this.verifier = JWT.require(algorithm)
                .withIssuer(jwtIssuer)
                .build();
    }

    public Optional<DecodedJWT> verify(String token) {
        try {
            DecodedJWT verify = verifier.verify(token);
            return Optional.of(verify);
        } catch (JWTVerificationException exception) {
            return Optional.empty();
        }
    }

    public String create(User create) {
        return JWT.create()
                .withIssuer(jwtIssuer)
                .withClaim("username", create.getUsername())
                .withClaim("role", create.getRole().getAuthority())
                .sign(algorithm);
    }
}
