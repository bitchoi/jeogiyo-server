package com.bitchoi.jeogiyoserver.controller;

import com.bitchoi.jeogiyoserver.dto.PhoneDto;
import com.bitchoi.jeogiyoserver.dto.SignUpDto;
import com.bitchoi.jeogiyoserver.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/registrations")
public class RegistrationController {

    private final UserService userService;

    @PostMapping
    public void signUp(@RequestBody SignUpDto signUpDto){
        userService.create(signUpDto);
    }

    @GetMapping("/email-check")
    public Map<String, Object> emailExistCheck(@RequestParam String email){
        Map<String, Object> res = new HashMap<>();
        res.put("result", userService.emailExistCheck(email));
        return res;
    }

    @GetMapping("/phone-check")
    public PhoneDto phoneAuthCheck(@RequestParam String phone){
        return userService.phoneAuthCheck(phone);
    }

}
