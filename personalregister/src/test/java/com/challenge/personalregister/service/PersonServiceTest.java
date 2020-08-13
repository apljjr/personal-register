package com.challenge.personalregister.service;

import com.challenge.personalregister.domain.Person;
import com.challenge.personalregister.domain.dto.request.PersonCreateRequestDTO;
import com.challenge.personalregister.domain.dto.request.PersonUpdateRequestDTO;
import com.challenge.personalregister.domain.dto.response.PersonResponseDTO;
import com.challenge.personalregister.exception.DataExistsException;
import com.challenge.personalregister.exception.DataNotFoundException;
import com.challenge.personalregister.repository.PersonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    private final String CPF_VALID = "40277263085";

    @InjectMocks
    private PersonService personService;

    @Mock
    private PersonRepository personRepository;

    @Test
    void createPerson_whenPersonNotExist_expectedSuccess() {
        when(personRepository.save(any())).thenReturn(documentPerson());
        when(personRepository.existsByCpf(any())).thenReturn(false);
        var result = personService.createPerson(requestCreateDtoSuccessTest());
        assertEquals(responseDtoTest(), result);
    }

    @Test
    void createPerson_whenPersonExist_expectedException() {
        Assertions.assertThrows(DataExistsException.class, () -> {
            when(personRepository.existsByCpf(any())).thenReturn(true);
            personService.createPerson(requestCreateDtoSuccessTest());
        });
    }

    @Test
    void updatePerson_whenPersonExist_expectedSuccess() {
        when(personRepository.save(any())).thenReturn(documentPerson());
        when(personRepository.findByCpf(any())).thenReturn(optionalPerson());
        var result = personService.updatePerson(CPF_VALID, requestUpdateDtoSuccessTest());
        assertEquals(responseDtoTest(), result);
    }

    @Test
    void updatePerson_whenPersonNotExist_expectedException() {
        Assertions.assertThrows(DataNotFoundException.class, () -> {
            when(personRepository.findByCpf(any())).thenReturn(Optional.empty());
            personService.updatePerson(any(), requestUpdateDtoSuccessTest());
        });
    }

    @Test
    void getAllPerson_whenValid_expectedSuccess() {
        when(personRepository.findAll(createPageRequest())).thenReturn(pageResponse());
        var result = personService.getAllPerson(createPageRequest());
        assertEquals(PersonResponseDTO.documentToDtoPage(pageResponse()), result);
    }

    @Test
    void getPersonByCpf_whenValid_expectedSuccess() {
        when(personRepository.findByCpf(CPF_VALID)).thenReturn(optionalPerson());
        var result = personService.getPersonByCpf(CPF_VALID);
        assertEquals(responseDtoTest(), result);
    }

    @Test
    void getPersonByCpf_whenValid_expectedException() {
        Assertions.assertThrows(DataNotFoundException.class, () -> {
            when(personRepository.findByCpf(CPF_VALID)).thenReturn(Optional.empty());
            personService.getPersonByCpf(CPF_VALID);
        });
    }

    @Test
    void deletePersonByCpf_whenValid_expectedSuccess() {
        doNothing().when(personRepository).deleteByCpf(CPF_VALID);
        personService.deletePersonByCpf(CPF_VALID);
        verify(personRepository).deleteByCpf(CPF_VALID);
    }

    private PersonCreateRequestDTO requestCreateDtoSuccessTest() {
        return PersonCreateRequestDTO.builder()
                .cpf(CPF_VALID)
                .name("João da Silva")
                .mail("joao@test.com")
                .placeBirth("Olinda/Brazil")
                .nationality("Brazil")
                .dateBirth(LocalDate.now())
                .gender("Masculino")
                .build();
    }

    private PersonUpdateRequestDTO requestUpdateDtoSuccessTest() {
        return PersonUpdateRequestDTO.builder()
                .name("João da Silva")
                .mail("joao@test.com")
                .placeBirth("Olinda/Brazil")
                .nationality("Brazil")
                .dateBirth(LocalDate.now())
                .gender("Masculino")
                .build();
    }

    private PersonResponseDTO responseDtoTest() {
        return PersonResponseDTO.builder()
                .cpf(CPF_VALID)
                .name("João da Silva")
                .mail("joao@test.com")
                .placeBirth("Olinda/Brazil")
                .nationality("Brazil")
                .gender("Masculino")
                .dateBirth(LocalDate.now())
                .build();
    }

    private Person documentPerson() {
        return Person.builder()
                .id("121lk1238123n12n132")
                .cpf(CPF_VALID)
                .name("João da Silva")
                .mail("joao@test.com")
                .placeBirth("Olinda/Brazil")
                .nationality("Brazil")
                .gender("Masculino")
                .dateBirth(LocalDate.now())
                .build();
    }

    private Optional<Person> optionalPerson() {
        return Optional.of(documentPerson());
    }

    private Page<Person> pageResponse() {
        return new PageImpl<>(List.of(documentPerson()));
    }

    private PageRequest createPageRequest() {
        return PageRequest.of(1, 1);
    }

}
