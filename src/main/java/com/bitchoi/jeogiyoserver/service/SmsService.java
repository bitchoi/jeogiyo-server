package com.bitchoi.jeogiyoserver.service;

import lombok.extern.log4j.Log4j2;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@Log4j2
public class SmsService {

    @Value("${SENDER.PHONE}")
    private String senderPhone;

    @Value("${SMS.API.KEY}")
    private String apiKey;

    @Value("${SMS.API.SECRET}")
    private String apiSecret;

    public void sendSms(String phone, int code) {

        String api_key = apiKey;
        String api_secret = apiSecret;
        Message message = new Message(api_key, api_secret);
        HashMap<String, String> params = new HashMap<>();

        params.put("to", senderPhone);
        params.put("from", phone);
        params.put("type", "SMS");
        params.put("text", "인증코드는" + code + "입니다.");
        params.put("app_version", "test app 1.2");

        try {
            JSONObject obj = (JSONObject) message.send(params);
            log.info("obj : {} ",obj.toString());
        } catch (CoolsmsException e) {
            log.debug("error message : {} ",e.getMessage());
            log.debug("error code : {} ",e.getCode());
        }
    }
}
