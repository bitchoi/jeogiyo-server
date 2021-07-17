package com.bitchoi.jeogiyoserver.service;

import com.bitchoi.jeogiyoserver.dto.PhoneDto;
import com.bitchoi.jeogiyoserver.dto.SignUpDto;
import com.bitchoi.jeogiyoserver.model.Role;
import com.bitchoi.jeogiyoserver.model.User;
import com.bitchoi.jeogiyoserver.model.redis.PhoneCheck;
import com.bitchoi.jeogiyoserver.repository.UserRepository;
import com.bitchoi.jeogiyoserver.repository.redis.PhoneCheckRepository;
import com.bitchoi.jeogiyoserver.security.UserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepo;

    private final PasswordEncoder passwordEncoder;

    private final SmsService smsService;

    private final PhoneCheckRepository phoneCheckRepo;

    public Optional<UserDetails> getUserByEmail(String email) {
        var optUser = userRepo.findByEmail(email);
        if(optUser.isPresent()){
            return Optional.of(new UserDetails(optUser.get()));
        }else return Optional.empty();
    }

    @Transactional
    public void create(SignUpDto signUpDto) {
        if(userRepo.findByEmail(signUpDto.getEmail()).isPresent()){
            throw new IllegalStateException("Email address is already in use");
        }

        var code = phoneCheckRepo.findById(signUpDto.getPhone()).orElseThrow(() -> new IllegalArgumentException("no phone auth code"));

        if(code.getExpiredOn().before(new Date())){
            throw new IllegalArgumentException("auth code expired");
        }

        if(!code.getCode().equals(signUpDto.getCode())){
            throw new IllegalArgumentException("auth code does not match");
        }

        User user = new User();
        user.setEmail(signUpDto.getEmail());
        user.setNickname(signUpDto.getNickname());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        user.setPhone(signUpDto.getPhone());
        user.setRole(Role.USER.getValue());
        userRepo.save(user);
    }

    @Transactional(readOnly = true)
    public Boolean emailExistCheck(String email) {
        return userRepo.emailExistCheck(email);
    }

    @Transactional(readOnly = true)
    public PhoneDto phoneAuthCheck(String phone) {
        Random random = new Random();
        int code = random.nextInt(888888) + 111111;

        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, 3);
        Date expiredOn = cal.getTime();

        Optional<PhoneCheck> existPhoneCheck = phoneCheckRepo.findById(phone);
        if(existPhoneCheck.isPresent()){
            existPhoneCheck.get().setCode(code);
            existPhoneCheck.get().setExpiredOn(expiredOn);
            phoneCheckRepo.save(existPhoneCheck.get());
        }else {
            PhoneCheck phoneCheck = new PhoneCheck();
            phoneCheck.setPhone(phone);
            phoneCheck.setCode(code);
            phoneCheck.setExpiredOn(expiredOn);
            phoneCheckRepo.save(phoneCheck);
        }

        smsService.sendSms(phone, code);
        PhoneDto res = new PhoneDto();
        res.setPhone(phone.substring(0, 3) + "-" + phone.substring(3, 7) + "-" + phone.substring(7, 11));
        return res;
    }
}
