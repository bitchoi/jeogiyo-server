package com.bitchoi.jeogiyoserver.controller;

import com.bitchoi.jeogiyoserver.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
}
