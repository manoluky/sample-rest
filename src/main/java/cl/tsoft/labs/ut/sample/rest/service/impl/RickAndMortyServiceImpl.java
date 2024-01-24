package cl.tsoft.labs.ut.sample.rest.service.impl;

import cl.tsoft.labs.ut.sample.rest.entitie.RickAndMortyCharacter;
import cl.tsoft.labs.ut.sample.rest.service.RickAndMortyService;
import cl.tsoft.labs.ut.sample.rest.service.config.Configuration;
import lombok.extern.slf4j.Slf4j;

import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

@Slf4j
@Singleton
public class RickAndMortyServiceImpl implements RickAndMortyService {

    private final Configuration properties;

    @Inject
    public RickAndMortyServiceImpl(Configuration properties) {
        this.properties = properties;
    }

    @Override
    public RickAndMortyCharacter getById(Long id) {
        String url = String.format("%s/%d", properties.getServiceUrlRickAndMortyCharacter(), id);

        if(id == null)
            throw new IllegalArgumentException("the character id is null");

        Client client = ClientBuilder.newBuilder()
            .build();

        try {

            return client
                .target(url)
                .request(MediaType.APPLICATION_JSON)
                .get(RickAndMortyCharacter.class);

        } catch(NotFoundException notFoundException) {
            String message = notFoundException.getResponse().readEntity(String.class);
            log.error("when character is not found un api: {}", message);
            return null;
        }
    }
}
