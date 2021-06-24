package com.bitchoi.jeogiyoserver.service;

import com.bitchoi.jeogiyoserver.model.User;
import com.bitchoi.jeogiyoserver.repository.UserRepository;
import com.bitchoi.jeogiyoserver.security.UserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Optional<UserDetails> getUserByEmail(String userEmail) {
        var optUser = userRepository.findByEmail(userEmail);
        if(optUser.isPresent()){
            return Optional.of(new UserDetails(optUser.get()));
        }else return Optional.empty();
    }

}
