package ru.itgroup.intouch.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;

public class JWTUtil {

    public Long getIdUser (String auth) {

        int i = auth.lastIndexOf('.');
        String withoutSignature = auth.substring(0, i+1);
        Jwt<Header, Claims> untrusted = Jwts.parser().parseClaimsJwt(withoutSignature);
        Object id = untrusted.getBody().get("userId");

        return Long.valueOf(id.toString());
    }


}
