// package com.apapedia.catalog.security.jwt;

// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.stereotype.Component;

// import io.jsonwebtoken.Claims;
// import io.jsonwebtoken.Jwts;

// @Component
// public class JwtUtils {
//     @Value("${user.app.jwtSecret}")
//     private String jwtSecret;

//     @Value("${user.app.jwtExpirationMs}")
//     private int jwtExpirationMs;

//     public Claims decodeToken(String token) {
//         return Jwts.parser()
//                 .setSigningKey(jwtSecret)
//                 .parseClaimsJws(token)
//                 .getBody();
//     }
    
//     public boolean validateToken(String token) {
//         try {
//             Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
//             return true;
//         } catch (Exception e) {
//             return false;
//         }
//     }

//     public String getUserNameFromJwtToken(String token) {
//         return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
//     }

//     public String extractUserId(String token) {
//         Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
//         return claims.getSubject();
//     }
    


// }
