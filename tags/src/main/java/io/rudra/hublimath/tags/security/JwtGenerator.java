package io.rudra.hublimath.tags.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.rudra.hublimath.tags.entities.UserLogin;
import org.springframework.stereotype.Component;
import java.util.Date;
import static io.rudra.hublimath.tags.entities.Constants.ACCESS_TOKEN_VALIDITY_SECONDS;



@Component
public class JwtGenerator {


    public String generate(UserLogin jwtUser) {


        Claims claims = Jwts.claims()
                .setSubject(jwtUser.getName());
        claims.put("userId", String.valueOf(jwtUser.getId()));
        claims.put("role", jwtUser.getRole());


        return Jwts.builder()
                .setClaims(claims)
                .setIssuer("http://devglan.com")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_SECONDS*1000))
                .signWith(SignatureAlgorithm.HS512, "youtube")
                .compact();
    }
}
