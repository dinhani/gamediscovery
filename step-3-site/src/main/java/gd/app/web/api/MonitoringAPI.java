package gd.app.web.api;

import gd.infrastructure.cache.CacheProducer;
import gd.infrastructure.log.LogMarker;
import gd.infrastructure.log.LogProducer;
import gd.infrastructure.memory.Memory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/monitoring", produces = MediaType.APPLICATION_JSON_VALUE)
public class MonitoringAPI {

    @Autowired
    private ResponseHelper response;

    @Autowired
    private CacheProducer cache;

    @Autowired
    private Memory memory;

    // LOGGER
    private static final Logger LOGGER = LogProducer.getLogger(MonitoringAPI.class);

    @RequestMapping(value = "/cache", method = RequestMethod.GET)
    public ResponseEntity cache() {
        return response.found(cache.getStatistics());
    }

    @RequestMapping(value = "/memory", method = RequestMethod.GET)
    public ResponseEntity memory() {
        return response.found(memory.getStatistics());
    }

    // =========================================================================
    // EXCEPTION HANDLING
    // =========================================================================
    // UNKNOWN EXCEPTIONS
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity handleUnexpectedException(Exception e) {
        LOGGER.error(LogMarker.API, "Unhandled exception | ex={}", e.getMessage(), e);
        return response.internalServerError();
    }
}
