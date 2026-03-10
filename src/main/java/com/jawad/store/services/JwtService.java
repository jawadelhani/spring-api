package com.jawad.store.services;

import com.jawad.store.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {
    @Value("${spring.jwt.secret}")
    private String secret;

    public String generateAccessToken(User user) {
        final long tokenExpriration=300;  //5 min
        return generateToken(user, tokenExpriration);
    }

    public String generateRefreshToken(User user) {
        final long tokenExpriration=604800;  //7 days
        return generateToken(user, tokenExpriration);
    }

    private String generateToken(User user, long tokenExpriration) {
        return Jwts.builder()
                .subject(user.getId().toString())
                .claim("email", user.getEmail())
                .claim("name", user.getName())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * tokenExpriration))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }

    public boolean validateToken(String token) {
        //it throws an error if token not valid
        try{
            var claims = getClaims(token);

            //verify not expired (true or false)
            return claims.getExpiration().after(new Date());
        }
        catch (JwtException e){

            return false;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Long getUserIdFromToken(String token) {
        return Long.valueOf(getClaims(token).getSubject());
    }
}
