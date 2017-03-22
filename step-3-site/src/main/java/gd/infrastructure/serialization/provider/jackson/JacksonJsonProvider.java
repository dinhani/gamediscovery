package gd.infrastructure.serialization.provider.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gd.infrastructure.error.SerializationException;
import gd.infrastructure.serialization.JsonProvider;
import gd.infrastructure.steriotype.GDService;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;

@GDService
public class JacksonJsonProvider implements JsonProvider {

    @Autowired
    private ObjectMapper serializer;

    @Override
    public String serialize(Object object) {
        try {
            return serializer.writeValueAsString(object);
        } catch (JsonProcessingException ex) {
            throw new SerializationException(ex.getMessage(), ex);
        }
    }

    @Override
    public <T> T deserialize(String json, Class<T> type) {
        try {
            return serializer.readValue(json, type);
        } catch (IOException ex) {
            throw new SerializationException(ex.getMessage(), ex);
        }
    }

}
