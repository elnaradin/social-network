package ru.itgroup.intouch.aggregator.config.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.itgroup.intouch.dto.UserDto;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.function.Function;


@Service
@Slf4j
public class JWTUtil {

    @Value("${jwt.secret}")
    private String secret;

    public Long getUserIdFromHeader(@NotNull String authorizationHeader) {
        return extractUserId(authorizationHeader.replace("Bearer ", "")).longValue();
    }

    public String generateAccessToken(UserDto userDto) {
        final LocalDateTime now = LocalDateTime.now();
        final Instant accessExpirationInstant = now.plusDays(1)
                                                   .atZone(ZoneId.systemDefault()).toInstant();
        final Date accessExpiration = Date.from(accessExpirationInstant);

        String userId = Jwts.builder().setSubject(userDto.getEmail())
                            .claim("userId", userDto.getId())
                            .setExpiration(accessExpiration)
                            .signWith(SignatureAlgorithm.HS256, secret)
                            .compact();
        log.info("token created: " + userId);
        return userId;
    }

    public Integer extractUserId(String token) {
        return extractClaim(token, (claims) -> (Integer) claims.get("userId"));
    }

    public String generateRefreshToken(UserDto userDto) {
        final LocalDateTime now = LocalDateTime.now();
        final Instant refreshExpirationInstant = now.plusDays(30)
                                                    .atZone(ZoneId.systemDefault()).toInstant();
        final Date refreshExpiration = Date.from(refreshExpirationInstant);
        return Jwts.builder()
                   .setSubject(userDto.getEmail())
                   .setExpiration(refreshExpiration)
                   .signWith(SignatureAlgorithm.HS256, secret)
                   .compact();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        log.info("token received: " + token);
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }


    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, String userName) {
        return (!isTokenExpired(token) && extractUsername(token).equals(userName));
    }
}
