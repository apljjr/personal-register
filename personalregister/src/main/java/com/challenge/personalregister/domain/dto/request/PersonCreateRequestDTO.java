package com.challenge.personalregister.domain.dto.request;

import com.challenge.personalregister.domain.Person;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "PersonCreateRequest")
public class PersonCreateRequestDTO {

    @NotNull
    @CPF
    @ApiModelProperty(value = "CPF")
    private String cpf;

    @NotNull
    @ApiModelProperty(value = "Nome")
    private String name;

    @ApiModelProperty(value = "Gênero")
    private String gender;

    @Email
    @ApiModelProperty(value = "E-mail")
    private String mail;

    @Past
    @ApiModelProperty(value = "Data de nascimento")
    @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
    private LocalDate dateBirth;

    @ApiModelProperty(value = "Naturalidade")
    private String placeBirth;

    @ApiModelProperty(value = "Nacionalidade")
    private String nationality;

    public static Person dtoToDocument(PersonCreateRequestDTO personDTO) {
        return Person.builder()
                .cpf(personDTO.getCpf())
                .name(personDTO.getName())
                .mail(personDTO.getMail())
                .gender(personDTO.getGender())
                .dateBirth(personDTO.getDateBirth())
                .placeBirth(personDTO.getPlaceBirth())
                .nationality(personDTO.getNationality())
                .build();
    }

}
