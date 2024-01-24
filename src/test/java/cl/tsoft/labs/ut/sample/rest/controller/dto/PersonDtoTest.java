package cl.tsoft.labs.ut.sample.rest.controller.dto;

// Generated by CodiumAI

import cl.tsoft.labs.ut.sample.rest.controller.dto.PersonDto;

import org.junit.Test;
import static org.junit.Assert.*;

public class PersonDtoTest {


    // should create a PersonDto object with all fields correctly set
    @Test
    public void test_create_personDto_with_all_fields_correctly_set() {
        PersonDto personDto = PersonDto.builder()
                .id(1L)
                .rut("12345678-9")
                .fullName("John Doe")
                .birthDate("1990-01-01")
                .homeAddress("123 Main St")
                .cellPhone("+1234567890")
                .mail("john.doe@example.com")
                .build();

        assertNotNull(personDto);
        assertEquals((Long) 1L,
                personDto.getId());
        assertEquals("12345678-9", personDto.getRut());
        assertEquals("John Doe", personDto.getFullName());
        assertEquals("1990-01-01", personDto.getBirthDate());
        assertEquals("123 Main St", personDto.getHomeAddress());
        assertEquals("+1234567890", personDto.getCellPhone());
        assertEquals("john.doe@example.com", personDto.getMail());
    }
    //should update a PersonDto object with new values
    @Test
    public void test_update_personDto_with_new_values() {
        PersonDto personDto = PersonDto.builder()
                .fullName("John Doe")
                .birthDate("1990-01-01")
                .build();

        personDto.setRut("12345678-9");
        personDto.setHomeAddress("123 Main St");
        personDto.setCellPhone("+1234567890");
        personDto.setMail("john.doe@example.com");

        assertEquals("12345678-9", personDto.getRut());
        assertEquals("123 Main St", personDto.getHomeAddress());
        assertEquals("+1234567890", personDto.getCellPhone());
        assertEquals("john.doe@example.com", personDto.getMail());
    }
    //should create a PersonDto object with null values for all fields
    @Test
    public void test_create_personDto_with_null_values() {
        PersonDto personDto = new PersonDto();

        assertNull(personDto.getId());
        assertNull(personDto.getRut());
        assertNull(personDto.getFullName());
        assertNull(personDto.getBirthDate());
        assertNull(personDto.getHomeAddress());
        assertNull(personDto.getCellPhone());
        assertNull(personDto.getMail());
    }

    @Test
    public void test_create_personDto_with_very_long_fullName() {
        String longFullName = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed euismod, nisl ac aliquam tincidunt, nunc nisl tincidunt nunc, nec lacinia nisl mi id lectus. Nulla facilisi. Sed id semper mauris. Sed nec semper nunc. Nulla facilisi. Sed id semper mauris. Sed nec semper nunc. Nulla facilisi. Sed id semper mauris. Sed nec semper nunc.";

        PersonDto personDto = PersonDto.builder()
                .fullName(longFullName)
                .build();

        assertNotNull(personDto);
        assertNull(personDto.getId());
        assertNull(personDto.getRut());
        assertEquals(longFullName, personDto.getFullName());
        assertNull(personDto.getBirthDate());
        assertNull(personDto.getHomeAddress());
        assertNull(personDto.getCellPhone());
        assertNull(personDto.getMail());
    }

    @Test
    public void create_personDto_with_very_long_fullName() {
        String longFullName = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed euismod, nisl ac aliquam tincidunt, nunc nisl tincidunt nunc, nec lacinia nisl mi id lectus. Nulla facilisi. Sed id semper mauris. Sed nec semper nunc. Nulla facilisi. Sed id semper mauris. Sed nec semper nunc. Nulla facilisi. Sed id semper mauris. Sed nec semper nunc.";

        PersonDto personDto = PersonDto.builder()
                .fullName(longFullName)
                .build();

        assertNotNull(personDto);
        assertNull(personDto.getId());
        assertNull(personDto.getRut());
        assertEquals(longFullName, personDto.getFullName());
        assertNull(personDto.getBirthDate());
        assertNull(personDto.getHomeAddress());
        assertNull(personDto.getCellPhone());
        assertNull(personDto.getMail());
    }
}