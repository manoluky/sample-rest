package cl.tsoft.labs.ut.sample.rest.repository;

import cl.tsoft.labs.ut.sample.rest.entitie.Person;
import cl.tsoft.labs.ut.sample.rest.service.repository.PersonRepository;
import cl.tsoft.labs.ut.sample.rest.entitie.type.Rut;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Singleton
public class JpaPersonRepositoryImpl implements PersonRepository {

    @PersistenceContext(unitName = "persistenceUnit")
    EntityManager entityManager;

    @Override
    public Person create(Person person) {
        Person personByRut = this.getByRut(person.getRut());
        if(personByRut != null)
            throw new IllegalStateException("person already exists");

        entityManager.persist(person);
        return person;
    }

    @Override
    public Person update(Person person) {
        Person personByRut = this.getByRut(person.getRut());
        if(personByRut == null)
            throw new IllegalStateException("person does not exists");

        entityManager.persist(person);
        entityManager.persist(person);
        return person;
    }

    @Override
    public Person deleteById(Long idPerson) {
        Person personById = this.getById(idPerson);
        if(personById == null)
            throw new IllegalStateException("person does not exist");

        entityManager.remove(personById);
        return personById;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Person> getAll() {
        return (List<Person>) entityManager.createQuery("SELECT p FROM Person p").getResultList();
    }

    @Override
    public Person getById(Long idPerson) {
        return entityManager.find(Person.class, idPerson);
    }

    @SuppressWarnings("unchecked")
    public Person getByRut(Rut rut) {
        List<Person> personByRut = (List<Person>) entityManager.createQuery(
            "SELECT p FROM Person p WHERE p.rut = :rut")
            .setParameter("rut", rut)
            .getResultList();

        if( personByRut.isEmpty() )
            return null;

        return personByRut.get(0);
    }
}
