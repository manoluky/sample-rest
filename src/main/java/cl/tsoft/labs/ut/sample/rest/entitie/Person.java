package cl.tsoft.labs.ut.sample.rest.entitie;

import cl.tsoft.labs.ut.sample.rest.entitie.converter.LocalDateConverter;
import cl.tsoft.labs.ut.sample.rest.entitie.converter.RutConverter;
import cl.tsoft.labs.ut.sample.rest.entitie.type.Rut;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "persons")
public class Person implements Serializable {
	
    private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Convert(converter = RutConverter.class)
    @Column(name="rut", unique=true)
    private Rut rut;

    @Column(name="name")
    private String name;

    @Column(name="paternal_last_name")
    private String paternalLastName;

    @Column(name="maternal_last_name")
    private String maternalLastName;

    @Convert(converter = LocalDateConverter.class)
    @Column(name="birth_date", columnDefinition="DATE")
    private LocalDate birthDate;

    @Column(name="home_address")
    private String homeAddress;

    @Column(name="cell_phone")
    private String cellPhone;

    @Column(name="mail")
    private String mail;
}
