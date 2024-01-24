package cl.tsoft.labs.ut.sample.rest.service;

import cl.tsoft.labs.ut.sample.rest.helpers.PersonsDummyFactory;
import cl.tsoft.labs.ut.sample.rest.entitie.Person;
import cl.tsoft.labs.ut.sample.rest.entitie.type.Rut;
import cl.tsoft.labs.ut.sample.rest.service.exception.BusinessException;
import cl.tsoft.labs.ut.sample.rest.service.impl.PersonServiceImpl;
import cl.tsoft.labs.ut.sample.rest.service.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonServiceImplTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonServiceImpl personService;

    @BeforeEach
    public void beforeEach() {
        Mockito.reset(personRepository);
    }

    @Test
    @DisplayName("deleteById: should throw BusinessException when id is null")
    void deleteById_should_throw_BusinessException_when_id_is_null() {
        // Given
        Long id = null;

        // When
        BusinessException exception =  assertThrows(BusinessException.class, () -> personService.deleteById(id) );

        // Then
        assertEquals(exception.getMessage(), "id of person is null");
    }

    @Test
    @DisplayName("deleteById: should successfully delete a person")
    void deleteById_should_successfully_delete_a_person() throws BusinessException {
        // Given
        Person person = PersonsDummyFactory.getPersonWithId();
        when(personRepository.deleteById(person.getId())).thenReturn(person);

        // When
        Person personDeleted = personService.deleteById(person.getId());

        // Then
        assertEquals(person, personDeleted);
    }

    @Test
    @DisplayName("deleteById: should return null when person does not exist")
    void deleteById_should_return_null_when_person_does_not_exist() throws BusinessException {
        // Given
        when(personRepository.deleteById(any())).thenReturn(null);

        // When
        Person personDeleted = personService.deleteById(1234569L);

        // Then
        assertNull(personDeleted);
    }

    @Test
    void getAll() {

    }

    @Test
    @DisplayName("getByRut: should throw BusinessException when rut is null")
    void getByRut_should_throw_BusinessException_when_rut_is_null() {
        // When
        BusinessException exception = assertThrows(BusinessException.class, () -> personService.getByRut(null));

        // Then
        assertEquals(exception.getMessage(), "rut of person is null");
    }

    @Test
    @DisplayName("getByRut: should return a person when rut exist")
    void getByRut_should_return_a_person_when_rut_exist() throws BusinessException {
        // Given
        Person person = PersonsDummyFactory.getPersonWithId();
        when(personRepository.getByRut(person.getRut())).thenReturn(person);

        // When
        Person personByRut = personService.getByRut(person.getRut());

        // Then
        assertEquals(person, personByRut);
    }

    @Test
    @DisplayName("getByRut: should return null when person does not exist")
    void getByRut_should_return_null_when_person_does_not_exist() throws BusinessException {
        // Given
        when(personRepository.getByRut(any())).thenReturn(null);

        // When
        Person person = personService.getByRut( new Rut(12345678, 'K'));

        // Then
        assertNull(person);
    }

    @Test
    @DisplayName("createPerson: should successfully create a person")
    void createPerson_should_create_successfully_a_person() throws BusinessException {
        // Given
        Person person = PersonsDummyFactory.getPersonWithId();
        when(personRepository.create(person)).thenReturn(person);

        // When
        Person personWithoutId = PersonsDummyFactory.getPersonWithId();
        personWithoutId.setId(null);
        Person personCreated = personService.createPerson(person);

        // Then
        assertNotNull(personCreated);
        assertNotNull(personCreated.getId());

        personWithoutId.setId(personCreated.getId());
        assertEquals(personWithoutId, personCreated);
    }

    @Test
    @DisplayName("createPerson: should throw BusinessException when person is null")
    public void createPerson_should_throw_BusinessException_when_person_is_null() {
        // When
        BusinessException exception = assertThrows(BusinessException.class, () -> personService.createPerson(null));

        // Then
        assertEquals("person is null", exception.getMessage());
    }

    @Test
    @DisplayName("createPerson: should throw BusinessException when rut is null")
    public void createPerson_should_throw_BusinessException_when_rut_is_null() {
        // Given
        Person person = PersonsDummyFactory.getPersonWithoutId();
        person.setRut(null);

        // When
        BusinessException exception = assertThrows(BusinessException.class, () -> personService.createPerson(person));

        // Then
        assertEquals("rut is null or is invalid", exception.getMessage());
    }

    @Test
    @DisplayName("createPerson: should throw BusinessException when rut is invalid")
    public void createPerson_should_throw_BusinessException_when_rut_is_invalid() {
        // Given
        Person person = PersonsDummyFactory.getPersonWithoutId();
        person.setRut( new Rut(123456789, '0') );

        // When
        BusinessException exception = assertThrows(BusinessException.class, () -> personService.createPerson(person));

        // Then
        assertEquals("rut is null or is invalid", exception.getMessage());
    }

    @Test
    @DisplayName("createPerson: should throw BusinessException when name is null")
    public void createPerson_should_throw_BusinessException_when_name_is_null() {
        // Given
        Person person = PersonsDummyFactory.getPersonWithoutId();
        person.setName(null);

        // When
        BusinessException exception = assertThrows(BusinessException.class, () -> personService.createPerson(person));

        // Then
        assertEquals("name is null or empty", exception.getMessage());
    }

    @Test
    @DisplayName("createPerson: should throw BusinessException when name is empty")
    public void createPerson_should_throw_BusinessException_when_name_is_empty() {
        // Given
        Person person = PersonsDummyFactory.getPersonWithoutId();
        person.setName("");

        // When
        BusinessException exception = assertThrows(BusinessException.class, () -> personService.createPerson(person));

        // Then
        assertEquals("name is null or empty", exception.getMessage());
    }

    @Test
    @DisplayName("createPerson: should throw BusinessException when rut already exist")
    public void createPerson_should_throw_BusinessException_when_rut_already_exist() {
        // Given
        Person person = PersonsDummyFactory.getPersonWithoutId();
        when(personRepository.getByRut(person.getRut())).thenReturn(person);

        // When
        BusinessException exception = assertThrows(BusinessException.class, () -> personService.createPerson(person));

        // Then
        String messageExpected = String.format("person with rut %s already exists", person.getRut().toString());
        assertEquals(messageExpected, exception.getMessage());
    }

    @Test
    void updatePerson() {
    }
}