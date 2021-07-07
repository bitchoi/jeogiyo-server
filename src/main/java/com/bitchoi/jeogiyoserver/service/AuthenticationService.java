package com.bitchoi.jeogiyoserver.service;

import com.bitchoi.jeogiyoserver.dto.JwtRequest;
import com.bitchoi.jeogiyoserver.dto.JwtResponse;
import com.bitchoi.jeogiyoserver.model.RefreshToken;
import com.bitchoi.jeogiyoserver.model.User;
import com.bitchoi.jeogiyoserver.repository.RefreshTokenRepository;
import com.bitchoi.jeogiyoserver.repository.UserRepository;
import com.bitchoi.jeogiyoserver.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
public class AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final RefreshTokenRepository refreshTokenRepository;

    private final JwtUtils jwtUtils;

    @Value("${jwt.refreshToken.duration}")
    private long REFRESH_TOKEN_DURATION;

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
        User user;
        switch (jwtRequest.getGrantType()){
            case CLIENT_CREDENTIALS:
                String email = jwtRequest.getEmail();
                user = findByEmailAndPassword(email, jwtRequest.getPassword()).orElseThrow(() -> new IllegalArgumentException("not find user"));
                break;
            case REFRESH_TOKEN:
                var refreshToken = refreshTokenRepository.findByRefreshToken(jwtRequest.getRefreshToken()).orElseThrow(() -> new IllegalArgumentException("not find refresh token"));
                if(refreshToken.getExpiredOn().before(new Date())){
                    throw new IllegalStateException("expired refresh token");
                }
                user = refreshToken.getUser();
                if(user == null){
                    throw new IllegalStateException("not find user");
                }
                break;
            default:
                throw new IllegalStateException("Grant type cannot be null");
        }

        return processJwtResponse(user);
    }

    private JwtResponse processJwtResponse(User user) {
        final String email = user.getEmail();
        var res = jwtUtils.generateToken(email);
        var refreshToken = refreshTokenRepository.findById(user.getUserId());
        if(refreshToken.isPresent() && refreshToken.get().getExpiredOn().after(new Date())){
            res.setRefreshToken(refreshToken.get().getRefreshToken());
        }else {
            if(refreshToken.isPresent()){
                refreshTokenRepository.deleteById(user.getUserId());
            }
            res.setRefreshToken(UUID.randomUUID().toString().replace("-", "").toLowerCase());
            Date expiredDate = new Date(System.currentTimeMillis() + REFRESH_TOKEN_DURATION * 1000);
            var refreshTokenEnt = new RefreshToken(res.getRefreshToken(), user, expiredDate);
            refreshTokenRepository.save(refreshTokenEnt);
        }
        return res;
    }
}
