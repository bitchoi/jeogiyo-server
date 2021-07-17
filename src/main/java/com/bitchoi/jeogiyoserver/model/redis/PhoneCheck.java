package com.bitchoi.jeogiyoserver.model.redis;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.Date;

@Getter
@Setter
@RedisHash("phone-check")
public class PhoneCheck {

    @Id
    private String phone;

//    private String phone;

    private Integer code;

    private Date expiredOn;
}
