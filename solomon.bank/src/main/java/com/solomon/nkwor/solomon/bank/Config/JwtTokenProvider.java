package com.solomon.nkwor.solomon.bank.Config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app.jwt-expiration}")
    private long jwtExpirationDate;

    public String generateToken(Authentication authentication){
        String username = authentication.getName();
        Date currentDate = new Date();
        // this bit here allows us to calculate when our jwt token would expire based on when the user logs in
        Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(currentDate)
                .setExpiration(expireDate)
                .signWith(key())
                .compact();
    }

    public Key key(){
        byte [] bytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(bytes);
    }

    // this method gets the username from the token
    public String getUserName(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    // this method validates a token

    public boolean validateToken(String token){
        try{
            Jwts.parser()
                    .setSigningKey(key())
                    .build()
                    .parse(token);
            return true;
        } catch (ExpiredJwtException | IllegalArgumentException | SecurityException | MalformedJwtException e) {
            throw new RuntimeException(e);
        }
    }
}
