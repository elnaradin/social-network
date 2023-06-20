package ru.itgroup.intouch.aggregator.utils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CookieUtil {
    public String getJwt(@NotNull HttpHeaders headers) {
        List<String> cookies = headers.get("cookie");
        if (cookies == null) {
            return null;
        }

        String cookieName = "jwt=";
        for (String cookie : cookies) {
            if (cookie.contains(cookieName)) {
                String jwt = cookie.substring(cookie.indexOf(cookieName) + cookieName.length());
                if (jwt.contains(";")) {
                    jwt = jwt.substring(jwt.indexOf(";"));
                }

                return jwt;
            }
        }

        return null;
    }

    public String getJwt(@NotNull HttpServletRequest request) {
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        return header.split(" ")[1].trim();
    }
}
