package cl.tsoft.labs.ut.sample.rest.entitie.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Converter
public class LocalDateConverter implements AttributeConverter<LocalDate, Date>  {

    @Override
    public Date convertToDatabaseColumn(LocalDate attribute) {
        if(attribute == null) return null;
        return Date.from(attribute.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    @Override
    public LocalDate convertToEntityAttribute(Date dbData) {
        if(dbData == null) return null;
        return dbData.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
