package com.challenge.personalregister.repository;

import com.challenge.personalregister.domain.Person;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends MongoRepository<Person, String> {

    Boolean existsByCpf(String cpf);
    Optional<Person> findByCpf(String cpf);
}
