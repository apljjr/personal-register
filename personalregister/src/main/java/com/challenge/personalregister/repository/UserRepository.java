package com.challenge.personalregister.repository;

import com.challenge.personalregister.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    Boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);
}
