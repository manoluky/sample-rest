package cl.tsoft.labs.ut.sample.rest.controller.dto;

import cl.tsoft.labs.ut.sample.rest.entitie.Person;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-24T11:36:36-0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.7 (Eclipse Adoptium)"
)
public class PersonMapperImpl extends PersonMapper {

    @Override
    public PersonDto personToPersonDto(Person person) {
        if ( person == null ) {
            return null;
        }

        PersonDto.PersonDtoBuilder personDto = PersonDto.builder();

        personDto.id( person.getId() );
        if ( person.getBirthDate() != null ) {
            personDto.birthDate( DateTimeFormatter.ISO_LOCAL_DATE.format( person.getBirthDate() ) );
        }
        personDto.homeAddress( person.getHomeAddress() );
        personDto.cellPhone( person.getCellPhone() );
        personDto.mail( person.getMail() );

        personDto.fullName( person.getName() + " " + person.getPaternalLastName() + " " + person.getMaternalLastName() );
        personDto.rut( person.getRut() != null ? person.getRut().toString(): null );

        return personDto.build();
    }

    @Override
    public Person personDtoToPerson(PersonDto personDto) {
        if ( personDto == null ) {
            return null;
        }

        Person person = new Person();

        person.setName( fullNameToName( personDto.getFullName() ) );
        person.setPaternalLastName( fullNameToPaternalLastName( personDto.getFullName() ) );
        person.setMaternalLastName( fullNameToMaternalLastName( personDto.getFullName() ) );
        person.setRut( rutToRut( personDto.getRut() ) );
        person.setId( personDto.getId() );
        if ( personDto.getBirthDate() != null ) {
            person.setBirthDate( LocalDate.parse( personDto.getBirthDate() ) );
        }
        person.setHomeAddress( personDto.getHomeAddress() );
        person.setCellPhone( personDto.getCellPhone() );
        person.setMail( personDto.getMail() );

        return person;
    }
}
