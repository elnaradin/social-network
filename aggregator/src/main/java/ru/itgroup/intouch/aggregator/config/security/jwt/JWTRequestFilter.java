package ru.itgroup.intouch.aggregator.config.security.jwt;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.itgroup.intouch.aggregator.config.security.UserDetailsImpl;
import ru.itgroup.intouch.aggregator.service.UserDetailsServiceImpl;

import java.io.IOException;

import static org.springframework.util.ObjectUtils.isEmpty;

@Component
@RequiredArgsConstructor
@Slf4j
public class JWTRequestFilter extends OncePerRequestFilter {

    private final UserDetailsServiceImpl userDetailsService;
    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain chain)
            throws ServletException, IOException {
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        SecurityContext context = SecurityContextHolder.getContext();

        if (isEmpty(header) || !header.startsWith("Bearer ") || header.contains("undefined")) {
            logger.warn("no " + HttpHeaders.AUTHORIZATION + " header present");
            chain.doFilter(request, response);
            return;
        }

        final String token = header.split(" ")[1].trim();

        if (context.getAuthentication() != null
                && jwtUtil.validateToken(token, context.getAuthentication().getName())) {
            chain.doFilter(request, response);
            log.warn("token is not valid");
            return;
        }
        UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService
                .loadUserByUsername(jwtUtil.extractUsername(token));
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null,
                userDetails.getAuthorities()
        );
        authentication.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );
        context.setAuthentication(authentication);

        chain.doFilter(request, response);
    }
}
