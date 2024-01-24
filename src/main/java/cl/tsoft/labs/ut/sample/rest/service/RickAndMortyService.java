package cl.tsoft.labs.ut.sample.rest.service;

import cl.tsoft.labs.ut.sample.rest.entitie.RickAndMortyCharacter;

public interface RickAndMortyService {

    RickAndMortyCharacter getById(Long id);

}
