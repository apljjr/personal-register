package com.challenge.personalregister.service;

import com.challenge.personalregister.domain.dto.request.PersonRequestDTO;
import com.challenge.personalregister.domain.dto.response.PersonResponseDTO;
import com.challenge.personalregister.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public PersonResponseDTO getPersonById(String id) {
        var person = personRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("id não existe"));
        log.info("Pessoa encontrada, person={}", person);
        return PersonResponseDTO.documentToDto(person);
    }

    public PersonResponseDTO createPerson(PersonRequestDTO personDTO) {
        var person = personRepository.save(PersonRequestDTO.dtoToDocument(personDTO));
        log.info("Pessoa criada, person={}", person);
        return PersonResponseDTO.documentToDto(person);
    }

}
