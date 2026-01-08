package pt.estg.projetoes202526.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pt.estg.projetoes202526.domain.models.User;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class TokenService {
    @Value("${api.security.token.secret}")
    private String secret;
    @Value("{spring.application.name}")
    private String appName;

    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer(appName)
                    .withSubject(user.getEmail())
                    .withExpiresAt(generateExpirationDate())
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception) {
            throw new RuntimeException("ERROR while creating token", exception);
        }
    }

    public String validateToken(String token) {
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer(appName)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            return "";
        }
    }

    //Data de expiração do token e definido no plushours = 2 horas
    private Instant generateExpirationDate() {
        return LocalDateTime.now()
                .plusHours(2)
                .atZone(ZoneId.of("Europe/Lisbon"))
                .toInstant();
    }
}
