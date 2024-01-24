package cl.tsoft.labs.ut.sample.rest.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonDto {
    private Long id;
    private String rut;
    private String fullName;
    private String birthDate;
    private String homeAddress;
    private String cellPhone;
    private String mail;
}
