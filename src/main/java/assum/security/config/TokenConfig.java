package assum.security.config;

import java.time.Instant;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import assum.security.entity.User;

@Component
public class TokenConfig {

	private static String secret = "secret";
	
	public static String generateToken(User user) {
		
		Algorithm algorithm = Algorithm.HMAC256(secret);
		
		return JWT.create()
				.withClaim("UserId", user.getId())
				.withClaim("roles", user.getRoles().stream().map(Enum::name).toList())
				.withSubject(user.getEmail())
				.withExpiresAt(Instant.now().plusSeconds(86400))
				.withIssuedAt(Instant.now())
				.sign(algorithm);
	}

	public Optional<JWTUserData> validateToken(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);

	        DecodedJWT decode = JWT.require(algorithm)
	                .build()
	                .verify(token);

	        JWTUserData userData = JWTUserData.builder()
	                .userId(decode.getClaim("UserId").asLong())
	                .email(decode.getSubject())
	                .roles(decode.getClaim("roles").asList(String.class))
	                .build();

	        return Optional.of(userData);
		} catch (JWTVerificationException ex) {
			return Optional.empty();
		}
	}
}
