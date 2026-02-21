package com.jawad.store.services;

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

    public String generateToken(String email) {
        final long tokenExpriration=86400;  //1 day
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+1000*tokenExpriration))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }

    public boolean validateToken(String token) {
        //it throw an error if token not valid
        try{
            var claims=Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            //verify not expired (true or false)
            return claims.getExpiration().after(new Date());
        }
        catch (JwtException e){

            return false;
        }


    }
}
