package cl.tsoft.labs.ut.sample.rest.service.config;

import javax.ejb.Singleton;
import java.io.InputStream;
import java.util.Properties;

@Singleton
public class Configuration {

    private final Properties properties = new Properties();

    public Configuration() {
        try ( InputStream is = this.getClass().getResourceAsStream("/application.properties") ) {
            properties.load(is);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getServiceUrlRickAndMortyCharacter() {
        return properties.getProperty("service.url.rickandmorty.character");
    }
}
