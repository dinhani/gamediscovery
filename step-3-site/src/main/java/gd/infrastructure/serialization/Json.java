package gd.infrastructure.serialization;

import gd.infrastructure.steriotype.GDService;
import org.springframework.beans.factory.annotation.Autowired;

@GDService
public class Json {

    @Autowired
    private JsonProvider jsonProvider;

    // =========================================================================
    // SERIALIZE
    // =========================================================================
    public String dump(Object object) {
        return toJson(object);
    }

    public String toJson(Object object) {
        return jsonProvider.serialize(object);
    }

    // =========================================================================
    // DESERIALIZE
    // =========================================================================
    public <T> T parse(String json, Class<T> type) {
        return jsonProvider.deserialize(json, type);
    }

    public <T> T toClass(String json, Class<T> type) {
        return jsonProvider.deserialize(json, type);
    }

}
