package com.challenge.personalregister.domain.dto.request;

import com.challenge.personalregister.domain.User;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "UserRequest")
public class UserRequestDTO {

    @NotNull
    private String username;

    @NotNull
    private String password;

    public static User dtoToDocument(UserRequestDTO userRequestDTO) {
        return User.builder()
                .username(userRequestDTO.getUsername())
                .password(BCrypt.hashpw(userRequestDTO.password, BCrypt.gensalt(10)))
                .build();
    }

}
