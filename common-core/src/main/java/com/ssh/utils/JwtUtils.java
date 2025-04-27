package com.ssh.utils;


import com.ssh.dtos.TokenObject;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtils {

    public static final String SECRET = "5367566859703373367639792F423F452848284D6251655468576D5A71347437";


    public TokenObject generateToken(String userId, String sessionId) {
        return TokenObject.builder()
                .accessToken(generateAccessToken(userId, sessionId))
                .refreshToken(generateRefreshToken(userId, sessionId))
                .build();
    }

    public String generateAccessToken(String userId, String sessionId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("type", "ACCESS_TOKEN");
        claims.put("sessionId", sessionId);
        return createToken(claims, userId, 30 * 60); // 30 minutes
    }

    public String generateRefreshToken(String userId, String sessionId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("type", "ACCESS_TOKEN");
        claims.put("sessionId", sessionId);

        return createToken(claims, userId, 7 * 24 * 60 * 60); // 7 days
    }

    private String createToken(Map<String, Object> claims, String userId, long expirySeconds) {
        Instant now = Instant.now();
        Instant expiry = now.plusSeconds(expirySeconds);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userId)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expiry))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    private String createToken(Map<String, Object> claims, String userId) {
        Instant now = Instant.now(); // Always in UTC
        Instant expiry = now.plusSeconds(60 * 30);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userId)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expiry))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUserId(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token) {
        //final String username = extractUsername(token);
        return (!isTokenExpired(token));
    }
}

