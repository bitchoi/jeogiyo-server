package com.bitchoi.jeogiyoserver.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SignUpDto {

    private String email;

    private String nickname;

    private String password;

    private String role;

    private String phone;
}
