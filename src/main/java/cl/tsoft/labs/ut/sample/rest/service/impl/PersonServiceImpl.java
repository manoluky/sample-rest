package cl.tsoft.labs.ut.sample.rest.service.impl;

import cl.tsoft.labs.ut.sample.rest.entitie.Person;
import cl.tsoft.labs.ut.sample.rest.entitie.type.Rut;
import cl.tsoft.labs.ut.sample.rest.service.exception.BusinessException;
import cl.tsoft.labs.ut.sample.rest.service.repository.PersonRepository;
import cl.tsoft.labs.ut.sample.rest.service.PersonService;

import javax.ejb.Singleton;
import javax.inject.Inject;
import java.util.List;

@Singleton
public class PersonServiceImpl implements PersonService {

    @Inject
    private PersonRepository personRepository;

    @Override
    public Person deleteById(Long id) throws BusinessException {

        if(id == null)
            throw new BusinessException("id of person is null");

        return personRepository.deleteById(id);
    }

    @Override
    public List<Person> getAll() {
        return personRepository.getAll();
    }

    @Override
    public Person getByRut(Rut rut) throws BusinessException {

        if(rut == null)
            throw new BusinessException("rut of person is null");

        return personRepository.getByRut(rut);
    }

    @Override
    public Person createPerson(Person person) throws BusinessException {
        if(person == null)
            throw new BusinessException("person is null");

        Rut rut = person.getRut();
        if(rut == null || !rut.isValid())
            throw new BusinessException("rut is null or is invalid");

        if(person.getName() == null || person.getName().isEmpty())
            throw new BusinessException("name is null or empty");

        if(personRepository.getByRut(rut) != null)
            throw new BusinessException("person with rut " + rut + " already exists");

        return personRepository.create(person);
    }

    @Override
    public Person updatePerson(Person person) throws BusinessException {
        if(person == null)
            throw new BusinessException("person is null");

        Rut rut = person.getRut();
        if(rut == null || !rut.isValid())
            throw new BusinessException("rut is null or is invalid");

        if(person.getName() == null || person.getName().isEmpty())
            throw new BusinessException("name is null or empty");

        if(personRepository.getByRut(person.getRut()) == null)
            throw new BusinessException("person with rut " + rut + " does not exist");

        return personRepository.update(person);
    }
}
