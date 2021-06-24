package com.bitchoi.jeogiyoserver.service;

import com.bitchoi.jeogiyoserver.dto.JwtRequest;
import com.bitchoi.jeogiyoserver.dto.JwtResponse;
import com.bitchoi.jeogiyoserver.model.User;
import com.bitchoi.jeogiyoserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public Optional<User> findByEmailAndPassword(String email, String password) {
        Optional<User> optUser = userRepository.findByEmail(email);
        if (optUser.isPresent()) {
            User user = optUser.get();
            return passwordEncoder.matches(password, user.getPassword()) ? optUser : Optional.empty();
        }
        return Optional.empty();

    }

    @Transactional
    public JwtResponse authenticationForToken(JwtRequest jwtRequest) {
        String email = jwtRequest.getEmail();
        user = findByEmailAndPassword(email,jwtRequest.getPassword()).orElseThrow(() -> new Exception("email or password wrong"));

    }
}
