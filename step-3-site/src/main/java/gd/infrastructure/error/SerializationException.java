package gd.infrastructure.error;

public class SerializationException extends GameDiscoveryException {

    public SerializationException(String message) {
        super(message);
    }

    public SerializationException(String message, Throwable cause) {
        super(message, cause);
    }

}
