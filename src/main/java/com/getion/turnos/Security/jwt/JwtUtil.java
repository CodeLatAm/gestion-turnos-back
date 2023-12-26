package com.getion.turnos.Security.jwt;

import com.getion.turnos.Security.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtUtil {

    private final static Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    SecretKey secretKey = Keys.hmacShaKeyFor(SecurityConstants.SECRET.getBytes(StandardCharsets.UTF_8));

    public String generate(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuer(SecurityConstants.ISSUER)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + SecurityConstants.EXPIRATION_TIME))
                .signWith(secretKey)
                .compact();
    }

    public boolean validate(String token) {
        return (getUsername(token) != null && isExpired(token));
    }

    public String getUsername(String token) {
        Claims claims = getClaims(token);
        return claims.getSubject();
    }

    public boolean isExpired(String token) {
        Claims claims = getClaims(token);
        return claims.getExpiration().after(new Date(System.currentTimeMillis()));
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
    }

    public String generateToken(Authentication authentication){
        String username = authentication.getName();

        //UserDetails mainUser = (UserDetails) authentication.getPrincipal();
        //logger.error(mainUser.getUsername());
        return Jwts.builder().setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + SecurityConstants.EXPIRATION_TIME *1000))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }
}
