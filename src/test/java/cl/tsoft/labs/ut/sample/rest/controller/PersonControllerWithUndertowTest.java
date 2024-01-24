package cl.tsoft.labs.ut.sample.rest.controller;

import cl.tsoft.labs.ut.sample.rest.controller.dto.PersonDto;
import cl.tsoft.labs.ut.sample.rest.controller.dto.PersonMapper;
import cl.tsoft.labs.ut.sample.rest.service.exception.BusinessException;
import cl.tsoft.labs.ut.sample.rest.entitie.Person;
import cl.tsoft.labs.ut.sample.rest.service.PersonService;
import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;
import org.jboss.resteasy.test.TestPortProvider;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;

@ExtendWith(MockitoExtension.class)
public class PersonControllerWithUndertowTest {
    private static UndertowJaxrsServer server;

    @Mock
    private static PersonService personService;

    @InjectMocks
    private static PersonController personController = new PersonController();

    @ApplicationPath("/api")
    public static class MyApp extends Application
    {
        @Override
        public Set<Object> getSingletons() {
            HashSet<Object> objects = new HashSet<>();
            objects.add(personController);
            return objects;
        }
    }

    @BeforeAll
    public static void startServer() {
        server = new UndertowJaxrsServer().start();
        server.deploy(MyApp.class);
    }

    @AfterAll
    public static void stopServer() {
        if(server != null)
            server.stop();
    }

    private PersonDto newPersonDto;
    private Person existingPerson;

    @BeforeEach
    public void beforeEach() {
        reset(personService);

        newPersonDto = PersonDto.builder()
            .fullName("JOHN DOE LUCAS")
            .rut("60212830-K")
            .birthDate("1980-03-01")
            .cellPhone("+5692191920")
            .homeAddress("THE ADDRESS")
            .mail("john.doe.lucas@gmail.com")
            .build();

        existingPerson = PersonMapper.INSTANCE.personDtoToPerson(newPersonDto);
        existingPerson.setId(12345678L);
    }

    @Test
    @DisplayName("should return the request url")
    void should_return_the_request_url() {
        Client client = ClientBuilder.newClient();
        String url = TestPortProvider.generateURL("/api/persons/health/request-url");
        String val = client
            .target(url)
            .request()
            .get(String.class);

        assertEquals(url, val);
        client.close();
    }

    @Test
    @DisplayName("should create successful a person")
    void should_create_successful_a_person() throws BusinessException {
        // Given
        PersonDto personRequested = newPersonDto;
        Person personCreated = PersonMapper.INSTANCE.personDtoToPerson(personRequested);
        given(personService.createPerson(any())).willReturn(personCreated);

        // When
        Client client = ClientBuilder.newClient();
        String url = TestPortProvider.generateURL("/api/persons");
        Entity<PersonDto> entity = Entity.json(personRequested);

        PersonDto personResponded = client
            .target(url)
            .request()
            .post(entity, PersonDto.class);

        // Then
        PersonDto personExpected = PersonMapper.INSTANCE.personToPersonDto(personCreated);
        assertEquals(personExpected,personResponded);
    }

    @Test
    @DisplayName("should delete successful a person")
    void should_delete_successful_a_person() throws BusinessException {

        // Given
        Person personDeleted = existingPerson;
        Long personId = personDeleted.getId();
        given(personService.deleteById(personId)).willReturn(personDeleted);

        Client client = ClientBuilder.newClient();
        String url = TestPortProvider.generateURL("/api/persons/" + personId);

        // When
        PersonDto personResponded = client
            .target(url)
            .request()
            .delete(PersonDto.class);

        // Then
        PersonDto personExpected = PersonMapper.INSTANCE.personToPersonDto(personDeleted);
        assertEquals(personExpected, personResponded);
    }

    @Test
    @DisplayName("should return bad request when create person is invalid")
    void should_return_bad_request_when_create_person_is_invalid() throws BusinessException {
        // Given
        PersonDto personRequested = newPersonDto;
        Person person = PersonMapper.INSTANCE.personDtoToPerson(personRequested);
        given(personService.createPerson(person)).willThrow(new BusinessException("invalid person"));

        // When
        String url = TestPortProvider.generateURL("/api/persons");
        Client client = ClientBuilder.newClient();
        Entity<PersonDto> entity = Entity.json(personRequested);

        Response response = client
            .target(url)
            .request()
            .post(entity, Response.class);

        // Then
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        assertEquals("invalid person", response.readEntity(String.class));
    }
}
