package io.rudra.hublimath.tags.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.rudra.hublimath.tags.entities.UserLogin;
import org.springframework.stereotype.Component;

@Component
public class JwtValidator {


    private String secret = "youtube";

    public UserLogin validate(String token) {

        UserLogin jwtUser = null;
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();

            jwtUser = new UserLogin();

            jwtUser.setName(body.getSubject());
            jwtUser.setId(Long.parseLong((String) body.get("userId")));
            jwtUser.setRole((String) body.get("role"));
        }
        catch (Exception e) {
            System.out.println(e);
        }

        return jwtUser;
    }
}
