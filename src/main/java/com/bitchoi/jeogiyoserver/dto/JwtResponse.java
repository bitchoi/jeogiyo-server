package com.bitchoi.jeogiyoserver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class JwtResponse {

    private final String accessToken;

    private final Date expiredOn;

    @Setter
    private String refreshToken;

    @Builder
    public JwtResponse(String accessToken, Date expiredOn) {
        this.accessToken = accessToken;
        this.expiredOn = expiredOn;
    }
}
