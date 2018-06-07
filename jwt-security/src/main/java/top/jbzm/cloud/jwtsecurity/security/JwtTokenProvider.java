package top.jbzm.cloud.jwtsecurity.security;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;

import java.util.Date;

/**
 * @author jbzm
 * @date 2018上午8:45
 **/
@Slf4j
public class JwtTokenProvider {
    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${jwtExpirationInMs}")
    private int jwtExpirationInMs;

    /**
     * generate token use userInfo
     * @param authentication find from local io
     * @return String for token
     */
    public String generateToken(Authentication authentication) {
        UserInfo principal = (UserInfo) authentication.getPrincipal();
        Date now = new Date();
        Date expirationTime = new Date(now.getTime() + jwtExpirationInMs);
        return Jwts.builder()
                .setSubject(String.valueOf(principal.getId()))
                .setExpiration(expirationTime)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.ES256, jwtSecret)
                .compact();
    }

    /**
     * get user id form token
     * @param token token
     * @return user id
     */
    public Long getUserIdFromJWT(String token) {
        Claims userInfo = Jwts.parser().setSigningKey(jwtSecret)
                .parseClaimsJws(token).getBody();
        return Long.valueOf(userInfo.getSubject());
    }

    /**
     * check token format
     * @param authToken token
     * @return true/false
     */
    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }
}
