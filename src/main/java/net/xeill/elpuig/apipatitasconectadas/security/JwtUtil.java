package net.xeill.elpuig.apipatitasconectadas.security;

import java.sql.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import net.xeill.elpuig.apipatitasconectadas.models.UserModel;

@Component
public class JwtUtil {

    private final String SECRET_KEY = "patitasConetadas2025";

    public String generateToken(UserModel user) {
        return Jwts.builder()
            .setSubject(user.getEmail())
            .setIssuedAt(new Date(0))
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
            .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
            .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token)
            .getBody().getSubject();
    }
}