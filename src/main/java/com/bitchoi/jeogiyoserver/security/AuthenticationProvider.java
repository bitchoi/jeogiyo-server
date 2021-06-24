package com.bitchoi.jeogiyoserver.security;

import com.bitchoi.jeogiyoserver.service.UserService;
import com.bitchoi.jeogiyoserver.utils.JwtUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Log4j2
public class AuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    private final UserService userService;

    private final JwtUtils jwtUtils;

    public AuthenticationProvider(UserService userService, JwtUtils jwtUtils) {
        this.userService = userService;
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails,
                                                  UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {

    }

    @Override
    protected UserDetails retrieveUser(String username,
                                       UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        String jwtToken = authentication.getCredentials().toString();
        if(jwtUtils.isTokenExpired(jwtToken)){
            throw new AccountExpiredException("token is expired");
        }

        String userEmail = jwtUtils.getUserEmailFromToken(jwtToken);
        if(StringUtils.isEmpty(userEmail)){
            throw new UsernameNotFoundException("not find email");

        }
        var userDetails = userService.getUserByEmail(userEmail).orElseThrow(() -> new UsernameNotFoundException("user not find"));
        return userDetails;
    }
}
