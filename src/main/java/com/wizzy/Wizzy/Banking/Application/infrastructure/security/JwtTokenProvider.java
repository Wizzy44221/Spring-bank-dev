package com.wizzy.Wizzy.Banking.Application.infrastructure.security;


import com.fasterxml.jackson.databind.DatabindException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;


@Component
public class JwtTokenProvider {

    @Value("${app.jwt-secret}")
    private  String jwtSecret;



    @Value("${app.jwt-expiration}")
    private  Long jwtExpiration;


    public  String generateToken(Authentication authentication){
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expirationDate = new Date(currentDate.getTime() + jwtExpiration);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(currentDate)
                .setExpiration(expirationDate)
                .signWith(key())
                .compact();
    }

    private Key key() {
        byte[] bytes = Decoders.BASE64.decode(jwtSecret);

        return Keys.hmacShaKeyFor(bytes);

    }

    public  String getUsername(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getPayload();

        return claims.getSubject();
    }

    public boolean validateToken(String token){
        try {
            Jwts.parser()
                    .setSigningKey(key())
                    .build()
                    .parse(token);

            return  true;

        } catch (ExpiredJwtException e) {
            throw new RuntimeException(e);
        } catch (MalformedJwtException e) {
            throw new RuntimeException(e);
        } catch (SecurityException e) {
            throw new RuntimeException(e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }
}
