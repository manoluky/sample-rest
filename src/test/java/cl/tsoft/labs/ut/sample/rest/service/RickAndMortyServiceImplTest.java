package cl.tsoft.labs.ut.sample.rest.service;

import cl.tsoft.labs.ut.sample.rest.entitie.RickAndMortyCharacter;
import cl.tsoft.labs.ut.sample.rest.service.config.Configuration;
import cl.tsoft.labs.ut.sample.rest.service.impl.RickAndMortyServiceImpl;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RickAndMortyServiceImplTest {

    @RegisterExtension
    static WireMockExtension mockApi = WireMockExtension.newInstance()
        .options(wireMockConfig().dynamicPort())
        .build();

    @Mock
    private Configuration configuration;

    @InjectMocks
    private RickAndMortyServiceImpl rickAndMortyService;

    @BeforeEach
    void beforeEach() {
        String baseUrlOfMockApi = mockApi.getRuntimeInfo().getHttpBaseUrl();
        when(configuration.getServiceUrlRickAndMortyCharacter()).thenReturn(baseUrlOfMockApi + "/api/character");
    }

    @Test
    @DisplayName("getById: should return a character")
    void getById_should_return_a_character() {
        // Given
        Long id = 2L; // Morty Smith

        mockApi.stubFor(get(String.format("/api/character/%d", id))
            .willReturn(aResponse()
                .withStatus(Response.Status.OK.getStatusCode())
                .withHeader("Content-Type", MediaType.APPLICATION_JSON)
                .withBodyFile("rickAndMortyResponses/getCharacter.json"))
        );

        // When
        RickAndMortyCharacter character = rickAndMortyService.getById(id);

        // Then
        assertNotNull(character);
        assertEquals(id, character.getId());
        assertEquals("Morty Smith", character.getName());
        assertEquals("Alive", character.getStatus());
        assertEquals("Human", character.getSpecies());
    }

    @Test
    @DisplayName("getById: should return null when character not found")
    void getById_should_return_null_when_character_not_found() {
        // Given
        Long id = 999L;

        mockApi.stubFor(get(String.format("/api/character/%d", id))
            .willReturn(aResponse()
                .withStatus(Response.Status.NOT_FOUND.getStatusCode())
                .withHeader("Content-Type", MediaType.APPLICATION_JSON)
                .withBody("{\"error\":\"Character not found\"}"))
        );

        // When
        RickAndMortyCharacter character = rickAndMortyService.getById(id);

        // Then
        assertNull(character);
    }

}