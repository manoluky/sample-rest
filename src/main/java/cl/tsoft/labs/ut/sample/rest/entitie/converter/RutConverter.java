package cl.tsoft.labs.ut.sample.rest.entitie.converter;

import cl.tsoft.labs.ut.sample.rest.entitie.type.Rut;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class RutConverter implements AttributeConverter<Rut, String> {

    @Override
    public String convertToDatabaseColumn(Rut attribute) {
        if(attribute == null) return null;
        return attribute.toString();
    }

    @Override
    public Rut convertToEntityAttribute(String dbData) {
        if(dbData == null) return null;
        return Rut.valueOf(dbData);
    }

}
