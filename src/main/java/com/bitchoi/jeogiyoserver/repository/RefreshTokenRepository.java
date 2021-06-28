package com.bitchoi.jeogiyoserver.repository;

import com.bitchoi.jeogiyoserver.model.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {
}
