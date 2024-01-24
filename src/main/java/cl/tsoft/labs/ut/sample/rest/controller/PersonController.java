package cl.tsoft.labs.ut.sample.rest.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import cl.tsoft.labs.ut.sample.rest.controller.dto.PersonDto;
import cl.tsoft.labs.ut.sample.rest.controller.dto.PersonMapper;
import cl.tsoft.labs.ut.sample.rest.entitie.Person;
import cl.tsoft.labs.ut.sample.rest.entitie.RickAndMortyCharacter;
import cl.tsoft.labs.ut.sample.rest.entitie.type.Rut;
import cl.tsoft.labs.ut.sample.rest.service.PersonService;
import cl.tsoft.labs.ut.sample.rest.service.RickAndMortyService;
import cl.tsoft.labs.ut.sample.rest.service.exception.BusinessException;

@RequestScoped
@Path("/persons")
public class PersonController {

    @Inject
    private PersonService personService;

    @Inject
    private RickAndMortyService rickAndMortyService;

    @Context
    private UriInfo uriInfo;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/health/request-url")
    public String getRequestUrl() {
        return uriInfo.getRequestUri().toString();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/")
    public Response createPerson(PersonDto person) {
        try {
            Person newPerson = PersonMapper.INSTANCE.personDtoToPerson(person);
            newPerson = personService.createPerson(newPerson);

            PersonDto personToResponse = PersonMapper.INSTANCE.personToPersonDto(newPerson);
            return Response
                .status(Response.Status.CREATED)
                .entity(personToResponse)
                .build();

        } catch (Exception e) {
            return getResponseBadRequest(e);
        }
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response deletePerson(@PathParam("id") Long id) {
        try {
            Person deletedPerson = personService.deleteById(id);
            PersonDto dto = PersonMapper.INSTANCE.personToPersonDto(deletedPerson);

            return Response
                .status(Response.Status.OK)
                .entity(dto)
                .build();
        } catch (BusinessException e) {
            return getResponseBadRequest(e);
        }
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/")
    public Response updatePerson(PersonDto person) {
        try {
            Person personToUpdate = PersonMapper.INSTANCE.personDtoToPerson(person);
            personService.updatePerson(personToUpdate);

            return Response
                .status(Response.Status.OK)
                .entity(person)
                .build();

        } catch (Exception e) {
            return getResponseBadRequest(e);
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/")
    public Response selectAll() {
        List<Person> persons = personService.getAll();
        List<PersonDto> personsToResponse = persons.stream().map(person -> PersonMapper.INSTANCE.personToPersonDto(person)).collect(Collectors.toList());

        return Response
            .status(Response.Status.OK)
            .entity(personsToResponse)
            .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{rut}")
    public Response findByRut(@PathParam("rut") String rut) {
        try {

            Person person = personService.getByRut(Rut.valueOf(rut));
            if (person == null)
                return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();

            PersonDto personDto = PersonMapper.INSTANCE.personToPersonDto(person);
            return Response
                .status(Response.Status.FOUND)
                .entity(personDto)
                .build();

        } catch (BusinessException e) {
            return getResponseBadRequest(e);
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/characters/{id}")
    public Response findCharacter(@PathParam("id") Long idCharacter) {
        RickAndMortyCharacter character = rickAndMortyService.getById(idCharacter);
        return Response
            .status(Response.Status.FOUND)
            .entity(character)
            .build();
    }

    private static Response getResponseBadRequest(Exception exception) {
        return Response
            .status(Response.Status.BAD_REQUEST)
            .entity(exception.getMessage())
            .build();
    }
}
