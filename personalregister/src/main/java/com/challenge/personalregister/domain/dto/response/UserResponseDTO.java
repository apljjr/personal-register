package com.challenge.personalregister.domain.dto.response;

import com.challenge.personalregister.domain.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "UserResponse")
public class UserResponseDTO {

    @ApiModelProperty(value = "Nome de usuário")
    private String username;

    @ApiModelProperty(value = "Token JWT")
    private String token;

    public static UserResponseDTO documentToDto(User user, String token) {
        return UserResponseDTO.builder()
                .username(user.getUsername())
                .token(token)
                .build();
    }

}
