package com.challenge.personalregister.service;

import com.challenge.personalregister.domain.User;
import com.challenge.personalregister.domain.dto.request.UserRequestDTO;
import com.challenge.personalregister.domain.dto.response.PersonResponseDTO;
import com.challenge.personalregister.domain.dto.response.UserResponseDTO;
import com.challenge.personalregister.exception.AuthException;
import com.challenge.personalregister.exception.DataExistsException;
import com.challenge.personalregister.exception.DataNotFoundException;
import com.challenge.personalregister.repository.UserRepository;
import com.challenge.personalregister.util.Constants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Slf4j
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        if(userRepository.existsByUsername(userRequestDTO.getUsername())) {
            throw new DataExistsException();
        }
        var user = userRepository.save(UserRequestDTO.dtoToDocument(userRequestDTO));
        log.info("Usuário criado com sucesso, user={}", user);
        return UserResponseDTO.documentToDto(user, generateJWTToken(user));
    }

    public UserResponseDTO getByUsernameAndPassword(UserRequestDTO userRequestDTO) {
        var user = userRepository.findByUsername(userRequestDTO.getUsername())
                .orElseThrow(() -> {
                    throw new DataNotFoundException("Usuário não encontrado para geração de token");
                });
        log.info("Usuário encontrada, user={}", user);
        verifyPasswords(userRequestDTO.getPassword(), user.getPassword());
        return UserResponseDTO.documentToDto(user, generateJWTToken(user));
    }

    private void verifyPasswords(String password, String passwordCrypt) {
        log.info("Verificando senha de usuário");
        if(!BCrypt.checkpw(password, passwordCrypt)) {
            log.error("Senhas não conferem");
            throw new AuthException();
        }
    }

    private String generateJWTToken(User user) {
        long timestamp = System.currentTimeMillis();
        return Jwts.builder().signWith(SignatureAlgorithm.HS256, Constants.API_SECRET_KEY)
                .setIssuedAt(new Date(timestamp))
                .setExpiration(new Date(timestamp + Constants.TOKEN_VALIDITY))
                .claim("id", user.getId())
                .claim("username", user.getUsername())
                .compact();
    }

}
