package com.bitchoi.jeogiyoserver.controller;

import com.bitchoi.jeogiyoserver.dto.JwtRequest;
import com.bitchoi.jeogiyoserver.dto.JwtResponse;
import com.bitchoi.jeogiyoserver.dto.UserDto;
import com.bitchoi.jeogiyoserver.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/email-find")
    public UserDto findEmail(@RequestParam String name, @RequestParam String phone){
        return authenticationService.findEmail(name, phone);
    }
}
