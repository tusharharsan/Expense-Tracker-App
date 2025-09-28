package org.example.Service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.cglib.core.ClassInfo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTService {
    public static final String SECRET = "d4f9c725bb3f9a8e11f89b9e0e2d6d7301d07ab4fef12f5d2c1b87a3d7cfde4a";

     public String extractUsername(String token){
         return extractClaim(token , Claims::getSubject);
     }

    public <T> T extractClaim(String token, Function<Claims ,T> claimResolver) {
         final Claims claims = extractAllClaims( token);

         return claimResolver.apply(claims);
    }

    public Date extractExpiration(String token){
         return extractClaim(token  , Claims::getExpiration);
    }

    private Boolean isTokenExpired(String token){
         return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token , UserDetails userDetails){
         final String username = extractUsername(token);
         return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String GenerateToken(String username){
         Map<String , Object> claims = new HashMap<>();
         return createToken(claims , username);
    }

    private String createToken(Map<String , Object> claims , String username){
         return Jwts.builder()
                 .setClaims(claims)
                 .setSubject(username)
                 .setIssuedAt(new Date(System.currentTimeMillis()))
                 .setExpiration(new Date(System.currentTimeMillis()+1000*60*1))
                 .signWith(getSignkey(), SignatureAlgorithm.HS256).compact();
    }



    public Claims extractAllClaims(String token) {
         return Jwts
                 .parser()
                 .setSigningKey(getSignkey())
                 .build()
                 .parseClaimsJws(token)
                 .getBody();
    }

    private Key getSignkey() {
         byte[] keybytes = Decoders.BASE64.decode(SECRET);
         return Keys.hmacShaKeyFor(keybytes);
    }
}
