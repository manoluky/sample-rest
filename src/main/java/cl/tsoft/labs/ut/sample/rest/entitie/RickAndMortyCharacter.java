package cl.tsoft.labs.ut.sample.rest.entitie;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class RickAndMortyCharacter {
    Long id;
    String name;
    String status;
    String species;
    String type;
    String gender;
    String url;
    String created;
}
