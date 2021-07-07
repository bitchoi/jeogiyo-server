package com.bitchoi.jeogiyoserver.dto;

import com.bitchoi.jeogiyoserver.constant.GrantType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class JwtRequest {

    private String email;

    private String password;

    private String refreshToken;

    private GrantType grantType;
}
