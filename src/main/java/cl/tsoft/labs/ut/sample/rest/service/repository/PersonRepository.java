package cl.tsoft.labs.ut.sample.rest.service.repository;

import cl.tsoft.labs.ut.sample.rest.entitie.Person;
import cl.tsoft.labs.ut.sample.rest.entitie.type.Rut;

import java.util.List;

public interface PersonRepository {
    Person create(Person data);
    Person update(Person data);
    Person deleteById(Long idPerson);
    List<Person> getAll();
    Person getById(Long idPerson);
    Person getByRut(Rut rut);
}
