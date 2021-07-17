package com.bitchoi.jeogiyoserver.repository.redis;

import com.bitchoi.jeogiyoserver.model.redis.PhoneCheck;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PhoneCheckRepository extends CrudRepository<PhoneCheck, String> {

//    Optional<PhoneCheck> findByPhone(String phone);
}
