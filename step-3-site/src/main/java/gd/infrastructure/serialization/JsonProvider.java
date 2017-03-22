package gd.infrastructure.serialization;

public interface JsonProvider {

    public String serialize(Object object);

    public <T> T deserialize(String json, Class<T> type);
}
