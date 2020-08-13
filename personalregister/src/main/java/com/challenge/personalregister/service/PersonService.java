package com.challenge.personalregister.service;

import com.challenge.personalregister.domain.dto.request.PersonCreateRequestDTO;
import com.challenge.personalregister.domain.dto.request.PersonUpdateRequestDTO;
import com.challenge.personalregister.domain.dto.response.PersonResponseDTO;
import com.challenge.personalregister.exception.DataExistsException;
import com.challenge.personalregister.exception.DataNotFoundException;
import com.challenge.personalregister.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public PersonResponseDTO getPersonByCpf(String cpf) {
        var person = personRepository.findByCpf(cpf).orElseThrow(DataNotFoundException::new);
        log.info("Pessoa encontrada com sucesso, person={}", person);
        return PersonResponseDTO.documentToDto(person);
    }

    public PersonResponseDTO createPerson(PersonCreateRequestDTO personCreate) {
        if(personRepository.existsByCpf(personCreate.getCpf())) {
            throw new DataExistsException();
        }
        var person = personRepository.save(PersonCreateRequestDTO.dtoToDocument(personCreate));
        log.info("Pessoa criada com sucesso, person={}", person);
        return PersonResponseDTO.documentToDto(person);
    }

    public Page<PersonResponseDTO> getAllPerson(PageRequest pageRequest) {
        var pagePerson = personRepository.findAll(pageRequest);
        log.info("Consulta com sucesso, persons={}", pagePerson);
        return PersonResponseDTO.documentToDtoPage(pagePerson);
    }

    public  PersonResponseDTO updatePerson(String cpf, PersonUpdateRequestDTO personUpdate) {
        var personBeforeUpdate = personRepository.findByCpf(cpf);
        if(personBeforeUpdate.isEmpty()) {
            throw new DataNotFoundException("Registro não encontrado para atualização");
        }
        var personAfterUpdate = personRepository.save(PersonUpdateRequestDTO.dtoToDocument(personBeforeUpdate.get().getId(), cpf, personUpdate));
        log.info("Pessoa atualizada com sucesso, person={}", personAfterUpdate);
        return PersonResponseDTO.documentToDto(personAfterUpdate);
    }

    public void deletePersonByCpf(String cpf) {
        personRepository.deleteByCpf(cpf);
        log.info("Pessoa excluida com sucesso, cpf={}", cpf);
    }

}
