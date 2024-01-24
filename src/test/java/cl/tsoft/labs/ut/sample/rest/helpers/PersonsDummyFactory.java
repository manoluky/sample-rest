package cl.tsoft.labs.ut.sample.rest.helpers;

import cl.tsoft.labs.ut.sample.rest.controller.dto.PersonDto;
import cl.tsoft.labs.ut.sample.rest.entitie.Person;
import cl.tsoft.labs.ut.sample.rest.entitie.type.Rut;

import java.util.concurrent.atomic.AtomicLong;

public class PersonsDummyFactory {

    private static final AtomicLong nextId = new AtomicLong(1L);

    public static PersonDto getPersonDtoWithoutId() {
        return PersonDto.builder()
            .fullName("JOHN DOE LUCAS")
            .rut("60212830-K")
            .birthDate("1980-03-01")
            .cellPhone("+5692191920")
            .homeAddress("THE ADDRESS")
            .mail("john.doe.lucas@gmail.com")
            .build();
    }

    public static Person getPersonWithoutId() {
        Person person = new Person();
        person.setMail("john.doe.lucas@gmail.com");
        person.setRut(new Rut(60212830, 'K'));
        person.setName("JOHN");
        person.setHomeAddress("THE ADDRESS OF MY HOME");
        person.setCellPhone("+5692191540");
        person.setMaternalLastName("LUCAS");
        person.setPaternalLastName("DOE");
        return person;
    }

    public static Person getPersonWithId() {
        Person person = getPersonWithoutId();
        person.setId(getNextId());
        return person;
    }

    private static synchronized Long getNextId() {
        long id = nextId.get() + 1;
        nextId.set(id);
        return id;
    }
}
