package com.tekup.recrutement.services;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    private static final String SECRET_KEY = "L9YbQTto5xUQ+uruzkCLrzcBZFeGhzkDj1che4doVQD7tW28x4rPy/0hXbGzHam67dzS52M4P5RYiplIKKvXXHxGHOjhGs3TTUFntJ5yTJHa8YjrZh5pQTJ7KWbjKPbomoM5CY5v5HWA0gwxTsr4vd2fAdjQUSsrs7+ivmhOVxBrzpvlLBhVu3S8CPwvKAJfcSb+7vxcKsd/Vl37FpQBeXkRMPIuyNqCH/2lb/mHuitdBqsNVXm231RMFL4kq7istLPS5oKUOvEo+KLJ4o8n86ot9KiCBZbwcNfzrrh7p5gd3n84481oDXHXDTA1SwO4SRHppjdrqQLE1wX34pLQevtSOTD8g0yA/eCdBAd0ZxU=";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(String userName) {
        return createToken(new HashMap<>(), userName);
    }

    public String createToken(Map<String, Object> extraClaims, String userName) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build().parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
