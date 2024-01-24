package cl.tsoft.labs.ut.sample.rest.service;

import cl.tsoft.labs.ut.sample.rest.entitie.Person;
import cl.tsoft.labs.ut.sample.rest.entitie.type.Rut;
import cl.tsoft.labs.ut.sample.rest.service.exception.BusinessException;

import java.util.List;

public interface PersonService {
    Person deleteById(Long id) throws BusinessException;
    List<Person> getAll();
    Person getByRut(Rut rut) throws BusinessException;
    Person createPerson(Person person) throws BusinessException;
    Person updatePerson(Person person) throws BusinessException;
}
