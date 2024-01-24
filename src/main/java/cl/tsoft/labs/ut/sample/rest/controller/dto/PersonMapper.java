package cl.tsoft.labs.ut.sample.rest.controller.dto;

import cl.tsoft.labs.ut.sample.rest.entitie.Person;
import cl.tsoft.labs.ut.sample.rest.entitie.type.Rut;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class PersonMapper {

    public static PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    @Mapping(target = "fullName", expression = "java(person.getName() + \" \" + person.getPaternalLastName() + \" \" + person.getMaternalLastName())")
    @Mapping(target = "rut", expression = "java(person.getRut() != null ? person.getRut().toString(): null)")
    public abstract PersonDto personToPersonDto(Person person);

    @Mapping(source = "fullName", target = "name", qualifiedByName = "fullNameToName")
    @Mapping(source = "fullName", target = "paternalLastName", qualifiedByName = "fullNameToPaternalLastName")
    @Mapping(source = "fullName", target = "maternalLastName", qualifiedByName = "fullNameToMaternalLastName")
    @Mapping(source = "rut", target = "rut", qualifiedByName = "rutToRut")
    public abstract Person personDtoToPerson(PersonDto personDto);

    @Named("fullNameToName")
    String fullNameToName(String fullName) {
        if(fullName == null) return null;

        String[] parts = fullName.split("\\s+");
        if(parts.length == 0)
            return null;

        if(parts.length <= 3)
            return parts[0];

        StringBuilder sb = new StringBuilder(parts[0]);
        for(int i = 1; i < parts.length - 2; i++) {
            sb.append(" ").append(parts[i]);
        }

        return sb.toString();
    }

    @Named("fullNameToPaternalLastName")
    String fullNameToPaternalLastName(String fullName) {
        if(fullName == null) return null;

        String[] parts = fullName.split("\\s+");

        if(parts.length <= 1)
            return null;

        if(parts.length == 2)
            return parts[1];

        return parts[ parts.length - 2 ];
    }

    @Named("fullNameToMaternalLastName")
    String fullNameToMaternalLastName(String fullName) {
        if(fullName == null) return null;

        String[] parts = fullName.split("\\s+");

        if( parts.length <= 2)
            return null;

        return parts[ parts.length - 1 ];
    }

    @Named("rutToRut")
    Rut rutToRut(String rut) {
        if(rut == null) return null;
        return Rut.valueOf(rut);
    }
}
