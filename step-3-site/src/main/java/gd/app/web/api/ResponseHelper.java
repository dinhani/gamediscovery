package gd.app.web.api;

import gd.infrastructure.steriotype.GDService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@GDService
public class ResponseHelper {

    // =========================================================================
    // SUCCESS (200)
    // =========================================================================
    public ResponseEntity created() {
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public ResponseEntity updated() {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    public ResponseEntity deleted() {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    public ResponseEntity found(Object data) {
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }

    // =========================================================================
    // CLIENT ERRORS (400)
    // =========================================================================
    public ResponseEntity badRequest(Iterable<String> errors) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    public ResponseEntity badRequest(String error) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    public ResponseEntity alreadyExists(String error) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    public ResponseEntity notFound() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // =========================================================================
    // SERVER ERRORS (500)
    // =========================================================================
    public ResponseEntity internalServerError() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
