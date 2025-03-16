package org.kapildev333.ecommerce.utils;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private String secret = "f09e6ad66b289f8542b212cabba589a81f371ff13e1b7a2f93d34203d8addf19a7b0a189bf6d8ec6e552d52eb0e74ea1b8911f21b92c5853cf22657d1a77ae92bba8642d19a74dd3b2550e12f5bdaf5c829140ab3dd83ddec53cb5ac3f78e32ed0f7ffc61b477accf29fb5be24cf96cc601329699ae62a444a56a57c1224ba674cd1a05c15b88ad85fcc4cefcafc9f68298c15938717cdb0438fce2fde965f1d4744d8bd43f02b540c7676620a2663f7b1b8d4af7ce6329871bdcac9aa2d934817c0cf6bc2474757865226e17e5b47f4a2870d09732bff9948fac1d127446dad3ac1ee4189729eb800ad0fed308250136184c84220044f4a689f883307c51639";

    @Autowired
    private HttpServletRequest request;

    public String extractTokenFromRequest() {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

    public String generateToken(String email) {
        return Jwts.builder().setSubject(email).setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
                .signWith(SignatureAlgorithm.HS256, secret).compact();
    }

    public String extractEmail(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }


}
