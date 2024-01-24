package cl.tsoft.labs.ut.sample.rest.helpers;

import com.google.gson.Gson;
import org.jboss.resteasy.mock.MockHttpResponse;

import java.lang.reflect.Type;

public class GsonFacade {
    private static final Gson gsonMapper;

    static {
        gsonMapper = new Gson();
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        return gsonMapper.fromJson(json, clazz);
    }

    public static <T> T fromJson(String json, Type T) {
        return gsonMapper.fromJson(json, T);
    }

    public static String toJson(Object object) {
        return gsonMapper.toJson(object);
    }

    public static byte[] toJsonBytes(Object object) {
        String json = toJson(object);
        return json.getBytes();
    }

    public static <T> T fromResponse(MockHttpResponse response, Class<T> clazz) {
        String json = response.getContentAsString();
        return fromJson(json, clazz);
    }

    public static <T> T fromResponse(MockHttpResponse response, Type T) {
        String json = response.getContentAsString();
        return fromJson(json, T);
    }
}
