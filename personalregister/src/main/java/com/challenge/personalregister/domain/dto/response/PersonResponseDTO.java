package com.challenge.personalregister.domain.dto.response;

import com.challenge.personalregister.domain.Person;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "PersonResponse")
public class PersonResponseDTO {

    @ApiModelProperty(value = "Id no sistema")
    private String id;

    @ApiModelProperty(value = "CPF")
    private String cpf;

    @ApiModelProperty(value = "Nome")
    private String name;

    @ApiModelProperty(value = "Gênero")
    private String gender;

    @ApiModelProperty(value = "E-mail")
    private String mail;

    @ApiModelProperty(value = "Data de nascimento")
    @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
    private LocalDate dateBirth;

    @ApiModelProperty(value = "Naturalidade")
    private String placeBirth;

    @ApiModelProperty(value = "Nacionalidade")
    private String nationality;

    public static PersonResponseDTO documentToDto(Person person) {
        return PersonResponseDTO.builder()
//                .id(person.getId())
                .cpf(person.getCpf())
                .name(person.getName())
                .mail(person.getMail())
                .gender(person.getGender())
                .dateBirth(person.getDateBirth())
                .placeBirth(person.getPlaceBirth())
                .nationality(person.getNationality())
                .build();
    }

}
