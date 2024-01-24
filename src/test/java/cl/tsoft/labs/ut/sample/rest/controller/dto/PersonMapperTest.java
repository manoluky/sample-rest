package cl.tsoft.labs.ut.sample.rest.controller.dto;

import cl.tsoft.labs.ut.sample.rest.entitie.Person;
import cl.tsoft.labs.ut.sample.rest.entitie.type.Rut;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class PersonMapperTest {

    @Test
    @DisplayName("map Person to PersonDto")
    public void map_person_to_personDto() {
        // Given
        Person person = new Person();
        person.setId(12345L);
        person.setMail("john.doe@gmail.com");
        person.setName("JOHN");
        person.setBirthDate(LocalDate.of(1980,3,1));
        person.setRut(new Rut(20833673, '8'));
        person.setCellPhone("+56922845678");
        person.setHomeAddress("MY STREET 456");
        person.setMaternalLastName("LUCAS");
        person.setPaternalLastName("DOE");

        // When
        PersonDto personDto = PersonMapper.INSTANCE.personToPersonDto(person);

        // Then
        String rutExpected = person.getRut().toString();
        String fullNameExpected = person.getName() + " " + person.getPaternalLastName() + " " + person.getMaternalLastName();
        String birthDateExpected = String.format("%04d-%02d-%02d",
            person.getBirthDate().getYear(),
            person.getBirthDate().getMonthValue(),
            person.getBirthDate().getDayOfMonth());

        assertNotNull(personDto);
        assertEquals(fullNameExpected, personDto.getFullName());
        assertEquals(birthDateExpected, personDto.getBirthDate());
        assertEquals(rutExpected, personDto.getRut());
    }

    @Test
    @DisplayName("map PersonDto to Person")
    public void map_personDto_to_person() {
        // Given
        PersonDto personDto = new PersonDto();
        personDto.setMail("john.doe@gmail.com");
        personDto.setRut("20833673-8");
        personDto.setCellPhone("+56922845678");
        personDto.setBirthDate("1980-03-01");
        personDto.setHomeAddress("MY STREET 456");
        personDto.setId(12345L);
        personDto.setFullName("JOHN DOE LUCAS");

        // When
        Person person = PersonMapper.INSTANCE.personDtoToPerson(personDto);

        // Then
        String name = "JOHN";
        String paternalLastName = "DOE";
        String maternalLastName = "LUCAS";
        LocalDate birthDate = LocalDate.of(1980, 3, 1);

        assertNotNull(person);
        assertEquals(name, person.getName());
        assertEquals(paternalLastName, person.getPaternalLastName());
        assertEquals(maternalLastName, person.getMaternalLastName());
        assertEquals(birthDate, person.getBirthDate());
        assertEquals(personDto.getRut(), person.getRut().toString());
    }

    @Test
    @DisplayName("should map successful when person has null values")
    public void should_map_successful_when_Person_has_null_values() {
        // Then
        Assertions.assertDoesNotThrow( () -> PersonMapper.INSTANCE.personToPersonDto( new Person()) );
    }

    @Test
    @DisplayName("should map successful when PersonDto has null values")
    public void should_map_successful_when_PersonDto_has_null_values() {
        // Then
        Assertions.assertDoesNotThrow( () -> PersonMapper.INSTANCE.personDtoToPerson(new PersonDto()));
    }

    @Test
    @DisplayName("should map successful when fullName has two names")
    public void should_map_successful_when_fullName_has_two_names() {
        // Given
        PersonDto personDto = new PersonDto();
        personDto.setFullName("JOHN ARTHUR DOE LUCAS");

        // When
        Person person = PersonMapper.INSTANCE.personDtoToPerson(personDto);

        // Then
        assertEquals("JOHN ARTHUR", person.getName());
    }

    @Test
    @DisplayName("should map successful when fullName has only paternalLastName")
    public void should_map_successful_when_fullName_only_has_paternalLastName() {
        // Given
        PersonDto personDto = new PersonDto();
        personDto.setFullName("JOHN DOE");

        // When
        Person person = PersonMapper.INSTANCE.personDtoToPerson(personDto);

        // Then
        assertEquals("JOHN", person.getName());
        assertEquals("DOE", person.getPaternalLastName());
        assertNull(person.getMaternalLastName());
    }

}