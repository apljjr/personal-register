package com.challenge.personalregister.controller;

import com.challenge.personalregister.config.SwaggerConfig;
import com.challenge.personalregister.domain.dto.request.UserRequestDTO;
import com.challenge.personalregister.domain.dto.response.PersonResponseDTO;
import com.challenge.personalregister.domain.dto.response.UserResponseDTO;
import com.challenge.personalregister.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Slf4j
@Api(tags = {SwaggerConfig.CTRL_USER})
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "Geração de token JWT para usuário")
    @PostMapping(value = "/auth")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Token gerado com sucesso", response = PersonResponseDTO.class),
            @ApiResponse(code = 400, message = "Má solicitação para geração de token"),
            @ApiResponse(code = 404, message = "Usuário não localizado"),
            @ApiResponse(code = 405, message = "Método não permitido"),
            @ApiResponse(code = 500, message = "Sistema indisponível")})
    public ResponseEntity<?> createAuthUser(@RequestBody UserRequestDTO userRequestDTO) {
        log.info("Iniciando geração de token para usuário, user={}", userRequestDTO.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.getByUsernameAndPassword(userRequestDTO));
    }

    @ApiOperation(value = "Registro de usuário")
    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Usuário criado com sucesso", response = PersonResponseDTO.class),
            @ApiResponse(code = 400, message = "Má solicitação para criar usuário"),
            @ApiResponse(code = 404, message = "Usuário não localizado"),
            @ApiResponse(code = 405, message = "Método não permitido"),
            @ApiResponse(code = 500, message = "Sistema indisponível")})
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO userRequestDTO) {
        log.info("Iniciando criação de usuário, user={}", userRequestDTO.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userRequestDTO));
    }
}