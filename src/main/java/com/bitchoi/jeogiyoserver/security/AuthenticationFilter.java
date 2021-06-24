package com.bitchoi.jeogiyoserver.security;

import com.bitchoi.jeogiyoserver.utils.JwtUtils;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
public class AuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final JwtUtils jwtUtils;

    private static final String AUTHORIZATION_HEADER = "Authorization";

    public AuthenticationFilter(final RequestMatcher requiresAuth, JwtUtils jwtUtils) {
        super(requiresAuth);
        this.jwtUtils = jwtUtils;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        final String headerToken = request.getHeader(AUTHORIZATION_HEADER);
        String username = null;
        String jwtToken = null;

        if(headerToken != null && headerToken.startsWith("Bearer ")){
            jwtToken = headerToken.substring(7);
            try {
                username = jwtUtils.getUserEmailFromToken(jwtToken);
            }catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT Token");
            } catch (ExpiredJwtException e) {
                System.out.println("JWT Token has expired");
            }
            Authentication auth = new UsernamePasswordAuthenticationToken(username,null);
            return getAuthenticationManager().authenticate(auth);
        }

        throw new AuthenticationCredentialsNotFoundException("Login token not found");
    }

    @Override
    protected void successfulAuthentication(final HttpServletRequest request, final HttpServletResponse response,
                                            final FilterChain chain,
                                            final Authentication authResult) throws IOException, ServletException {
        SecurityContextHolder.getContext().setAuthentication(authResult);
        chain.doFilter(request, response);
    }
}
