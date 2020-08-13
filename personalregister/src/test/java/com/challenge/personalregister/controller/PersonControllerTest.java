package com.challenge.personalregister.controller;

import com.challenge.personalregister.domain.dto.request.PersonCreateRequestDTO;
import com.challenge.personalregister.domain.dto.request.PersonUpdateRequestDTO;
import com.challenge.personalregister.domain.dto.response.PersonResponseDTO;
import com.challenge.personalregister.exception.DataNotFoundException;
import com.challenge.personalregister.service.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    private final String CPF_VALID = "40277263085";
    private final String CPF_INVALID = "40277";

    @Test
    void createPerson_whenAllParametersValid_expectedSuccess() throws Exception {
        when(personService.createPerson(requestCreateDtoSuccessTest()))
                .thenReturn(responseDtoTest());
        mockMvc.perform(post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(responseDtoTest())))
                .andExpect(status().isCreated());
    }

    @Test
    void createPerson_whenAllParametersInvalid_expectedBadRequest() throws Exception {
        mockMvc.perform(post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(requestCreateDtoBadRequestTest())))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updatePerson_whenAllParametersValid_expectedSuccess() throws Exception {
        when(personService.updatePerson(CPF_VALID, requestUpdateDtoSuccessTest()))
                .thenReturn(responseDtoTest());
        mockMvc.perform(put(String.format("/person/%s", CPF_VALID))
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(responseDtoTest())))
                .andExpect(status().isOk());
    }

    @Test
    void updatePerson_whenAllParametersInvalid_expectedBadRequest() throws Exception {
        mockMvc.perform(put(String.format("/person/%s", CPF_INVALID))
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(requestUpdateDtoBadRequestTest())))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAllPerson_whenAllParametersValid_expectedSuccess() throws Exception {
        when(personService.getAllPerson(PageRequest.of(0, 50)))
                .thenReturn(Mockito.any());
        mockMvc.perform(get("/person")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getAllPerson_whenAllParametersInvalid_expectedBadRequest() throws Exception {
        mockMvc.perform(get("/person?size=0")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getPersonByCpf_whenAllParametersValid_expectedSuccess() throws Exception {
        when(personService.getPersonByCpf(CPF_VALID))
                .thenReturn(Mockito.any());
        mockMvc.perform(get(String.format("/person/%s", CPF_VALID))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getPersonByCpf_whenAllParametersInvalid_expectedBadRequest() throws Exception {
        mockMvc.perform(get(String.format("/person/%s", CPF_INVALID))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deletePersonByCpf_whenAllParametersValid_expectedSuccess() throws Exception {
        doNothing().when(personService).deletePersonByCpf(CPF_VALID);
        mockMvc.perform(delete(String.format("/person/%s", CPF_VALID))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void deletePersonByCpf_whenAllParametersInvalid_expectedBadRequest() throws Exception {
        mockMvc.perform(delete(String.format("/person/%s", CPF_INVALID))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
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

    private PersonCreateRequestDTO requestCreateDtoBadRequestTest() {
        return PersonCreateRequestDTO.builder()
                .cpf(CPF_INVALID)
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

    private PersonUpdateRequestDTO requestUpdateDtoBadRequestTest() {
        return PersonUpdateRequestDTO.builder()
                .name("João da Silva")
                .mail("joao")
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
                .build();
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
