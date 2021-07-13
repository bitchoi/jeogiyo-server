package com.bitchoi.jeogiyoserver.controller;

import com.bitchoi.jeogiyoserver.dto.JwtRequest;
import com.bitchoi.jeogiyoserver.dto.JwtResponse;
import com.bitchoi.jeogiyoserver.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/auth")
    public JwtResponse auth(HttpServletResponse response, @RequestBody JwtRequest jwtRequest){
        var res = authenticationService.authenticationForToken(jwtRequest);
        Cookie cookie = new Cookie("refreshToken", res.getRefreshToken());
        cookie.setHttpOnly(true);
        cookie.setMaxAge(60 * 60 * 24 * 30);
        cookie.setPath("/");
        response.addCookie(cookie);
        return res;
    }
}
