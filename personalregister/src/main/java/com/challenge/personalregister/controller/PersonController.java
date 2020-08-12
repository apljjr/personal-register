package com.challenge.personalregister.controller;

import com.challenge.personalregister.config.SwaggerConfig;
import com.challenge.personalregister.domain.dto.request.PersonRequestDTO;
import com.challenge.personalregister.domain.dto.response.PersonResponseDTO;
import com.challenge.personalregister.service.PersonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping("/person")
@Slf4j
@Api(tags = {SwaggerConfig.CTRL_REGISTER})
public class PersonController {

    @Autowired
    private PersonService personService;

    @ApiOperation(value = "Adicionar pessoa")
    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Pessoa criado com sucesso", response = PersonRequestDTO.class),
            @ApiResponse(code = 400, message = "Má solicitação para consulta de pessoa"),
            @ApiResponse(code = 401, message = "Ausência de autorização"),
            @ApiResponse(code = 403, message = "Usuário não autorizado a realizar consulta de pessoa"),
            @ApiResponse(code = 404, message = "Pessoa não localizado com o cpf informado"),
            @ApiResponse(code = 405, message = "Método não permitido"),
            @ApiResponse(code = 500, message = "Sistema indisponível") })
    public ResponseEntity<PersonResponseDTO> createPerson (
            @ApiParam(value = "Dados da pessoa", required = true) @Valid @RequestBody PersonRequestDTO person) {
        log.info("Iniciando criação de pessoa, person={}", person);
        return ResponseEntity.ok(personService.createPerson(person));
    }

    @ApiOperation(value = "Consultar pessoa por id")
    @GetMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso na consulta de pessoa", response = PersonRequestDTO.class),
            @ApiResponse(code = 400, message = "Má solicitação para consulta de pessoa"),
            @ApiResponse(code = 401, message = "Ausência de autorização"),
            @ApiResponse(code = 403, message = "Usuário não autorizado a realizar consulta de pessoa"),
            @ApiResponse(code = 404, message = "Pessoa não localizado com o id informado"),
            @ApiResponse(code = 405, message = "Método não permitido"),
            @ApiResponse(code = 500, message = "Sistema indisponível") })
    public ResponseEntity<PersonResponseDTO> getPersonById (
            @ApiParam(value = "Id da pessoa", example = "1Kem99asnnd9", required = true) @PathVariable String id) {
        log.info("Iniciando consulta de pessoa, id={}", id);
        return ResponseEntity.ok(personService.getPersonById(id));
    }

    @ApiOperation(value = "Consultar pessoa por CPF")
    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso na consulta de pessoa", response = PersonRequestDTO.class),
            @ApiResponse(code = 400, message = "Má solicitação para consulta de pessoa"),
            @ApiResponse(code = 401, message = "Ausência de autorização"),
            @ApiResponse(code = 403, message = "Usuário não autorizado a realizar consulta de pessoa"),
            @ApiResponse(code = 404, message = "Pessoa não localizado com o cpf informado"),
            @ApiResponse(code = 405, message = "Método não permitido"),
            @ApiResponse(code = 500, message = "Sistema indisponível") })
    public ResponseEntity<Page<PersonResponseDTO>> getPersonAll (
            @ApiParam(value = "CPF", example = "56988877945", required = true) @PathVariable String cpf) {
        return null;
    }

}
