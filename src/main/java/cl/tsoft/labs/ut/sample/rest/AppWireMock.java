package cl.tsoft.labs.ut.sample.rest;

import cl.tsoft.labs.ut.sample.rest.controller.dto.PersonDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

public class AppWireMock {
    public static void main(String[] args) throws Exception {
        WireMockServer wireMockServer = new WireMockServer(options()
            .port(8080)
            .usingFilesUnderDirectory("src/main/resources/")
        ); //No-args constructor will start on port 8080, no HTTPS
        wireMockServer.start();

        // How to Return a Text Plain Body
        wireMockServer.stubFor(get(urlEqualTo("/text/plain"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "text/plain")
                .withBody("Hello world!")));

        // How to return a Json Body
        PersonDto person = new PersonDto();
        person.setId(123L);
        person.setBirthDate("1975-12-21");
        person.setMail("mauricio.camara@tsoftlatam.com");
        person.setRut("13005188-K");
        person.setFullName("Mauricio Cámara");

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(person);

        wireMockServer.stubFor(get(urlEqualTo("/text/json"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody(json)));

        // How to response from file
        wireMockServer.stubFor(get(urlEqualTo("/file/json"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBodyFile("getCharacter.json")));

        // How to process request with headers
        wireMockServer.stubFor(post(urlEqualTo("/with/headers"))
                .withHeader("x-api-key", equalTo("123"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("getCharacter.json")));

        // How to process request with headers
        wireMockServer.stubFor(post(urlEqualTo("/with/headers"))
                .withHeader("x-api-key", equalTo("456"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "plain/text")
                        .withBody("Hello World!")));
    }
}
