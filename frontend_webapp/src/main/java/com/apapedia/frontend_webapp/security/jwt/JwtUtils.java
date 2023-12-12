package com.apapedia.frontend_webapp.security.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtUtils {

    @Value("${user.app.jwtSecret}")
    private String jwtSecret;

    @Value("${user.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    public Claims decodeToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT Signature: {}", e.getMessage());
        }
        catch(MalformedJwtException e){
            logger.error("Invalid JWT token: {}", e.getMessage());
        }
        catch(ExpiredJwtException e){
            logger.error("JWT token is expired: {}", e.getMessage());
        }
        catch(UnsupportedJwtException e){
            logger.error("JWT token is unsupported: {}", e.getMessage());
        }
        catch(IllegalArgumentException e){
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

}
