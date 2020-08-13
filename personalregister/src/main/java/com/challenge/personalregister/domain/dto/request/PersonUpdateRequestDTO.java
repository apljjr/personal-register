package com.challenge.personalregister.domain.dto.request;

import com.challenge.personalregister.domain.Person;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "PersonUpdateRequest")
public class PersonUpdateRequestDTO {

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

    public static Person dtoToDocument(String id, String cpf, PersonUpdateRequestDTO personUpdate) {
        return Person.builder()
                .id(id)
                .cpf(cpf)
                .name(personUpdate.getName())
                .mail(personUpdate.getMail())
                .gender(personUpdate.getGender())
                .dateBirth(personUpdate.getDateBirth())
                .placeBirth(personUpdate.getPlaceBirth())
                .nationality(personUpdate.getNationality())
                .build();
    }

}
