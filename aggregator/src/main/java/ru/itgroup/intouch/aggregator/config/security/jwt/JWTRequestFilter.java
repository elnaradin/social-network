package ru.itgroup.intouch.aggregator.config.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.ContentType;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.itgroup.intouch.aggregator.config.security.UserDetailsImpl;
import ru.itgroup.intouch.aggregator.service.UserDetailsServiceImpl;
import ru.itgroup.intouch.client.exceptionHandling.ErrorResponse;

import java.io.IOException;

import static org.springframework.util.ObjectUtils.isEmpty;

@Component
@RequiredArgsConstructor
@Slf4j
public class JWTRequestFilter extends OncePerRequestFilter {

    private final UserDetailsServiceImpl userDetailsService;
    private final JWTUtil jwtUtil;
    private final ObjectMapper objectMapper;

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

        UserDetailsImpl userDetails;
        try {
            userDetails = (UserDetailsImpl) userDetailsService
                    .loadUserByUsername(jwtUtil.extractUsername(token));
        } catch (Exception e){
            sendUnauthorizedError(response, e);
            return;
        }
        
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
    private void sendUnauthorizedError(HttpServletResponse response, Exception e) throws IOException {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        response.sendError(status.value());
        response.setContentType(ContentType.APPLICATION_JSON.getMimeType());
        ErrorResponse errorResponse = new ErrorResponse(status.value(), status.name(), e.getMessage(), null);
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
