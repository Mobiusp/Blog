package com.mobiusp.backstage.utils;

import io.jsonwebtoken.*;

import java.util.Date;
import java.util.Objects;

public class JWTUtil {
    private static final long time = 1000 * 60 * 30;


    public static Boolean checkToken (String token) {
        if (Objects.equals(token, "") || token == null) return false;
        JwtParser jwtParser = Jwts.parser();
        Jws<Claims> claimsJws = null;
        try {
            claimsJws = jwtParser.setSigningKey(signature).parseClaimsJws(token);
        } catch (JwtException e) {
            return false;
        }
        if (claimsJws.getBody().getExpiration().getTime() <= System.currentTimeMillis()) return false;
        return true;
    }
}
