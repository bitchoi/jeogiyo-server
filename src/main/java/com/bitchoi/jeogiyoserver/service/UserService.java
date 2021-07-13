package com.bitchoi.jeogiyoserver.service;

import com.bitchoi.jeogiyoserver.dto.PhoneDto;
import com.bitchoi.jeogiyoserver.dto.SignUpDto;
import com.bitchoi.jeogiyoserver.model.Role;
import com.bitchoi.jeogiyoserver.model.User;
import com.bitchoi.jeogiyoserver.repository.UserRepository;
import com.bitchoi.jeogiyoserver.security.UserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public Optional<UserDetails> getUserByEmail(String email) {
        var optUser = userRepository.findByEmail(email);
        if(optUser.isPresent()){
            return Optional.of(new UserDetails(optUser.get()));
        }else return Optional.empty();
    }

    @Transactional
    public void create(SignUpDto signUpDto) {
        if(userRepository.findByEmail(signUpDto.getEmail()).isPresent()){
            throw new IllegalStateException("Email address is already in use");
        }
        User user = new User();
        user.setEmail(signUpDto.getEmail());
        user.setNickname(signUpDto.getNickname());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        user.setPhone(signUpDto.getPhone());
        user.setRole(Role.USER.getValue());
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public Boolean emailExistCheck(String email) {
        return userRepository.emailExistCheck(email);
    }

    @Transactional(readOnly = true)
    public PhoneDto phoneAuthCheck(String phone) {
        //TODO phone auth
        PhoneDto res = new PhoneDto();
        res.setPhone(phone.substring(0, 3) + "-" + phone.substring(3, 7) + "-" + phone.substring(7, 11));
        return res;
    }
}
