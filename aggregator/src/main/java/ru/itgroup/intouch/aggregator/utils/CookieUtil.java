package ru.itgroup.intouch.aggregator.utils;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CookieUtil {
    private final JWTUtil jwtUtil;

    public String getUsernameFromCookie(@NotNull HttpHeaders headers) {
        List<String> cookies = headers.get("cookie");
        if (cookies == null) {
            return null;
        }

        String jwt = "";
        String cookieName = "jwt=";
        for (String cookie : cookies) {
            if (cookie.contains(cookieName)) {
                jwt = cookie.substring(cookie.indexOf(cookieName) + cookieName.length());
                if (jwt.contains(";")) {
                    jwt = jwt.substring(jwt.indexOf(";"));
                }

                break;
            }
        }

        return jwtUtil.extractUsername(jwt);
    }
}
