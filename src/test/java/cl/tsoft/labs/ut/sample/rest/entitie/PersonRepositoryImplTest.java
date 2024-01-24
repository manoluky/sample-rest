package cl.tsoft.labs.ut.sample.rest.entitie;

import cl.tsoft.labs.ut.sample.rest.helpers.PersonsDummyFactory;
import cl.tsoft.labs.ut.sample.rest.repository.JpaPersonRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonRepositoryImplTest {

    @Mock
    EntityManager entityManager;

    @InjectMocks
    JpaPersonRepositoryImpl personRepository;

    @BeforeEach
    public void beforeEach() {
        Mockito.reset(entityManager);
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void getAll() {
    }

    @Test
    void getById() {
    }

    @Test
    @DisplayName("getByRut: should return a persona when rut exists")
    void getByRut_should_return_a_person_when_rut_exists() {

        // Given
        Person person = PersonsDummyFactory.getPersonWithId();
        List<Person> listOfPersons = Collections.singletonList(person);
        Query query = Mockito.mock(Query.class);
        when(query.setParameter("rut", person.getRut())).thenReturn(query);
        when(query.getResultList()).thenReturn(listOfPersons);
        when(entityManager.createQuery(anyString())).thenReturn(query);

        // When
        Person personByRut = personRepository.getByRut(person.getRut());

        // Then
        assertNotNull(personByRut);
        assertEquals(person, personByRut);
    }

    @Test
    @DisplayName("getByRut: should return null when rut not found")
    void getByRut_should_return_null_when_rut_not_found() {

        // Given
        Person person = PersonsDummyFactory.getPersonWithId();
        List<Person> listOfPersons = Collections.emptyList();
        Query query = Mockito.mock(Query.class);
        when(query.setParameter("rut", person.getRut())).thenReturn(query);
        when(query.getResultList()).thenReturn(listOfPersons);
        when(entityManager.createQuery(anyString())).thenReturn(query);

        // When
        Person personByRut = personRepository.getByRut(person.getRut());

        // Then
        assertNull(personByRut);
    }
}