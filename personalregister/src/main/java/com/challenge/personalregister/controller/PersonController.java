package com.challenge.personalregister.controller;

import com.challenge.personalregister.config.SwaggerConfig;
import com.challenge.personalregister.domain.dto.request.PersonCreateRequestDTO;
import com.challenge.personalregister.domain.dto.request.PersonUpdateRequestDTO;
import com.challenge.personalregister.domain.dto.response.PersonResponseDTO;
import com.challenge.personalregister.service.PersonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;


@RestController
@RequestMapping("/person")
@Slf4j
@Api(tags = {SwaggerConfig.CTRL_PERSON})
@Validated
public class PersonController {

    @Autowired
    private PersonService personService;

    @ApiOperation(value = "Adicionar pessoa")
    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Pessoa criado com sucesso", response = PersonResponseDTO.class),
            @ApiResponse(code = 400, message = "Má solicitação para consulta de pessoa"),
            @ApiResponse(code = 401, message = "Ausência de autorização"),
            @ApiResponse(code = 403, message = "Usuário não autorizado a realizar consulta de pessoa"),
            @ApiResponse(code = 404, message = "Pessoa não localizado com o cpf informado"),
            @ApiResponse(code = 405, message = "Método não permitido"),
            @ApiResponse(code = 500, message = "Sistema indisponível") })
    public ResponseEntity<PersonResponseDTO> createPerson (
            @ApiParam(value = "Dados da pessoa", required = true) @Valid @RequestBody PersonCreateRequestDTO personCreate) {
        log.info("Iniciando criação de pessoa, create={}", personCreate);
        return ResponseEntity.status(HttpStatus.CREATED).body(personService.createPerson(personCreate));
    }

    @ApiOperation(value = "Atualizar pessoa")
    @PutMapping("/{cpf}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Pessoa atualizada com sucesso", response = PersonResponseDTO.class),
            @ApiResponse(code = 400, message = "Má solicitação para atualização de pessoa"),
            @ApiResponse(code = 401, message = "Ausência de autorização"),
            @ApiResponse(code = 403, message = "Usuário não autorizado a realizar atualização de pessoa"),
            @ApiResponse(code = 404, message = "Pessoa não localizado com o cpf informado"),
            @ApiResponse(code = 405, message = "Método não permitido"),
            @ApiResponse(code = 500, message = "Sistema indisponível") })
    public ResponseEntity<PersonResponseDTO> updatePerson (
            @ApiParam(value = "CPF da pessoa", example = "63464419061", required = true) @PathVariable @CPF String cpf,
            @ApiParam(value = "Dados da pessoa", required = true) @Valid @RequestBody PersonUpdateRequestDTO personUpdate) {
        log.info("Iniciando atualização de pessoa, cpf={}, update={}", cpf, personUpdate);
        return ResponseEntity.ok(personService.updatePerson(cpf, personUpdate));
    }

    @ApiOperation(value = "Consultar pessoa por CPF")
    @GetMapping("/{cpf}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso na consulta de pessoa", response = PersonResponseDTO.class),
            @ApiResponse(code = 400, message = "Má solicitação para consulta de pessoa"),
            @ApiResponse(code = 401, message = "Ausência de autorização"),
            @ApiResponse(code = 403, message = "Usuário não autorizado a realizar consulta de pessoa"),
            @ApiResponse(code = 404, message = "Pessoa não localizado com o cpf informado"),
            @ApiResponse(code = 405, message = "Método não permitido"),
            @ApiResponse(code = 500, message = "Sistema indisponível") })
    public ResponseEntity<PersonResponseDTO> getPersonByCPF (
            @ApiParam(value = "CPF da pessoa", example = "63464419061", required = true) @PathVariable @CPF String cpf) {
        log.info("Iniciando consulta de pessoa, cpf={}", cpf);
        return ResponseEntity.ok(personService.getPersonByCpf(cpf));
    }

    @ApiOperation(value = "Consultar todas as pessoas")
    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso na consulta de pessoas"),
            @ApiResponse(code = 400, message = "Má solicitação para consulta de pessoas"),
            @ApiResponse(code = 401, message = "Ausência de autorização"),
            @ApiResponse(code = 403, message = "Usuário não autorizado a realizar consulta de pessoas"),
            @ApiResponse(code = 404, message = "Não localizado com os parâmetros informados"),
            @ApiResponse(code = 405, message = "Método não permitido"),
            @ApiResponse(code = 500, message = "Sistema indisponível") })
    public ResponseEntity<Page<PersonResponseDTO>> getAllPerson (
            @ApiParam(value = "Número da página", example = "0") @RequestParam(value = "page", defaultValue = "0") @PositiveOrZero int page,
            @ApiParam(value = "Número de registros por página", example = "50") @RequestParam(value = "size", defaultValue = "50") @Positive int size) {
        log.info("Iniciando consulta de pessoas, page={}, size={}", page, size);
        return ResponseEntity.ok(personService.getAllPerson(PageRequest.of(page, size)));
    }

    @ApiOperation(value = "Excluir pessoa por CPF")
    @DeleteMapping("/{cpf}")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Sucesso na consulta de pessoa"),
            @ApiResponse(code = 400, message = "Má solicitação para excluir pessoa"),
            @ApiResponse(code = 401, message = "Ausência de autorização"),
            @ApiResponse(code = 403, message = "Usuário não autorizado a realizar exclusão de pessoa"),
            @ApiResponse(code = 404, message = "Pessoa não localizado com o cpf informado"),
            @ApiResponse(code = 405, message = "Método não permitido"),
            @ApiResponse(code = 500, message = "Sistema indisponível") })
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deletePersonByCPF (
            @ApiParam(value = "CPF da pessoa", example = "63464419061", required = true) @PathVariable @CPF String cpf) {
        log.info("Iniciando exclusão de pessoa, cpf={}", cpf);
        personService.deletePersonByCpf(cpf);
    }

}
