package com.bitchoi.jeogiyoserver.repository;

import com.bitchoi.jeogiyoserver.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Query("SELECT CASE WHEN count(u) > 0 THEN false ELSE true END FROM User u WHERE u.email = " +
            ":email")
    boolean emailExistCheck(String email);

}
