package gd.infrastructure.error;

public class GameDiscoveryException extends RuntimeException {

    public GameDiscoveryException() {
    }

    public GameDiscoveryException(String message) {
        super(message);
    }

    public GameDiscoveryException(String message, Throwable cause) {
        super(message, cause);
    }

    public GameDiscoveryException(Throwable cause) {
        super(cause);
    }

    public GameDiscoveryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
