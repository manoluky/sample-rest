package cl.tsoft.labs.ut.sample.rest.controller;

import cl.tsoft.labs.ut.sample.rest.controller.dto.PersonDto;
import cl.tsoft.labs.ut.sample.rest.controller.dto.PersonMapper;
import cl.tsoft.labs.ut.sample.rest.service.exception.BusinessException;
import cl.tsoft.labs.ut.sample.rest.helpers.GsonFacade;
import cl.tsoft.labs.ut.sample.rest.entitie.Person;
import cl.tsoft.labs.ut.sample.rest.service.PersonService;
import cl.tsoft.labs.ut.sample.rest.entitie.type.Rut;
import com.google.gson.reflect.TypeToken;
import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;

@ExtendWith(MockitoExtension.class)
class PersonControllerWithMockTest {

    private static Dispatcher dispatcher;

    @Mock
    private static PersonService personService;

    @InjectMocks
    private static PersonController personController = new PersonController();

    @BeforeAll
    public static void beforeAll() {
        dispatcher = MockDispatcherFactory.createDispatcher();
        dispatcher.getRegistry().addSingletonResource(personController);
    }

    @AfterAll
    static void tearDown() {
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
    void should_return_the_request_url() throws URISyntaxException {
        MockHttpRequest request = MockHttpRequest.get("/persons/health/request-url");
        MockHttpResponse response = new MockHttpResponse();
        dispatcher.invoke(request, response);

        assertEquals("/persons/health/request-url", response.getContentAsString());
    }

    @Test
    @DisplayName("should create successful a person")
    void should_create_successful_a_person() throws URISyntaxException, BusinessException {
        // Given
        PersonDto personRequested = newPersonDto;
        Person personCreated = PersonMapper.INSTANCE.personDtoToPerson(personRequested);
        personCreated.setId(12345678L);
        given(personService.createPerson(any())).willReturn(personCreated);

        // When
        MockHttpRequest request = MockHttpRequest.post("/persons");
        request.contentType(MediaType.APPLICATION_JSON_TYPE);
        request.content(GsonFacade.toJsonBytes(personRequested));

        MockHttpResponse response = new MockHttpResponse();
        dispatcher.invoke(request, response);

        // Then
        assertEquals(HttpServletResponse.SC_CREATED, response.getStatus());

        String personResponded = response.getContentAsString();
        String personExpected = GsonFacade.toJson(PersonMapper.INSTANCE.personToPersonDto(personCreated));
        assertEquals(personExpected, personResponded);
    }

    @Test
    @DisplayName("should update successful a person")
    void should_update_successful_a_person() throws URISyntaxException, BusinessException {
        // Given
        PersonDto personToUpdate = PersonMapper.INSTANCE.personToPersonDto(existingPerson);
        Person personUpdated = existingPerson;
        given(personService.updatePerson(any())).willReturn(personUpdated);

        // When
        MockHttpRequest request = MockHttpRequest.put("/persons");
        request.contentType(MediaType.APPLICATION_JSON_TYPE);
        request.content(GsonFacade.toJsonBytes(personToUpdate));

        MockHttpResponse response = new MockHttpResponse();
        dispatcher.invoke(request, response);

        // Then
        assertEquals(HttpServletResponse.SC_OK, response.getStatus());

        PersonDto personResponded = GsonFacade.fromResponse(response, PersonDto.class);
        assertEquals(personToUpdate, personResponded);
    }

    @Test
    @DisplayName("should delete successful a person")
    void should_delete_successful_a_person() throws URISyntaxException, BusinessException {
        // Given
        Person personDeleted = existingPerson;
        Long personId = personDeleted.getId();
        given(personService.deleteById(personId)).willReturn(personDeleted);

        // When
        MockHttpRequest request = MockHttpRequest.delete("/persons/" + personId);
        MockHttpResponse response = new MockHttpResponse();
        dispatcher.invoke(request, response);

        // Then
        assertEquals(HttpServletResponse.SC_OK, response.getStatus());

        PersonDto personResponded = GsonFacade.fromResponse(response, PersonDto.class);
        PersonDto personExpected = PersonMapper.INSTANCE.personToPersonDto(personDeleted);
        assertEquals(personExpected, personResponded);
    }

    @Test
    @DisplayName("should return all persons")
    void should_return_all_persons() throws URISyntaxException {
        // Given
        List<Person> persons = new ArrayList<>();
        for(long i=0;i < 5; i++) {
            Person person = existingPerson;
            person.setId(i);
            persons.add(person);
        }
        given(personService.getAll()).willReturn(persons);

        // When
        MockHttpRequest request = MockHttpRequest.get("/persons");
        MockHttpResponse response = new MockHttpResponse();
        dispatcher.invoke(request, response);

        // Then
        assertEquals(HttpServletResponse.SC_OK, response.getStatus());

        Type listOfPersons = new TypeToken<ArrayList<PersonDto>>() {}.getType();
        List<PersonDto> personsResponded = GsonFacade.fromResponse(response, listOfPersons);
        assertEquals(persons.size(), personsResponded.size());

        List<PersonDto> personsExpected = persons.stream().map(person -> PersonMapper.INSTANCE.personToPersonDto(person)).collect(Collectors.toList());
        assertEquals(personsExpected, personsResponded);
    }

    @Test
    @DisplayName("should return person when rut exists")
    void should_return_person_when_rut_exists() throws URISyntaxException, BusinessException {
        // Given
        Person person = existingPerson;
        given(personService.getByRut(person.getRut())).willReturn(person);

        // When
        MockHttpRequest request = MockHttpRequest.get("/persons/" + person.getRut());
        MockHttpResponse response = new MockHttpResponse();
        dispatcher.invoke(request, response);

        // Then
        assertEquals(HttpServletResponse.SC_FOUND, response.getStatus());
        PersonDto personByRut = GsonFacade.fromResponse(response, PersonDto.class);
        assertEquals(person.getRut().toString(), personByRut.getRut());
    }

    @Test
    @DisplayName("should return 404 when person does not exist")
    void should_return_404_when_person_does_not_exist() throws URISyntaxException, BusinessException {
        // Given
        String rut = "1-9";
        given(personService.getByRut(Rut.valueOf(rut))).willReturn(null);

        // When
        MockHttpRequest request = MockHttpRequest.get("/persons/" + rut);
        MockHttpResponse response = new MockHttpResponse();
        dispatcher.invoke(request, response);

        // Then
        assertEquals(HttpServletResponse.SC_NOT_FOUND, response.getStatus());
    }

    @Test
    @DisplayName("should return bad request when create person is invalid")
    void should_return_bad_request_when_create_person_is_invalid() throws URISyntaxException, BusinessException {
        // Given
        PersonDto personDto = newPersonDto;
        Person person = PersonMapper.INSTANCE.personDtoToPerson(personDto);
        given(personService.createPerson(person)).willThrow(new BusinessException("invalid person"));

        // When
        MockHttpRequest request = MockHttpRequest.post("/persons");
        request.contentType(MediaType.APPLICATION_JSON_TYPE);
        request.content(GsonFacade.toJsonBytes(personDto));

        MockHttpResponse response = new MockHttpResponse();
        dispatcher.invoke(request, response);

        // Then
        assertEquals(HttpServletResponse.SC_BAD_REQUEST, response.getStatus());
        String message = response.getContentAsString();
        assertEquals("invalid person", message);
    }
}