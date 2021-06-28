package com.bitchoi.jeogiyoserver.controller;

import com.bitchoi.jeogiyoserver.dto.SignUpDto;
import com.bitchoi.jeogiyoserver.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/registrations")
public class RegistrationController {

    private final UserService userService;

    @PostMapping
    public void signUp(@RequestBody SignUpDto signUpDto){
        userService.create(signUpDto);
    }
}
