package gd.app.web.api;

import gd.domain.entities.Entities;
import gd.domain.entities.entity.ConceptEntry;
import gd.domain.entities.entity.ConceptType;
import gd.domain.recommendations.Recommendations;
import gd.domain.recommendations.dto.GameDiscoverySearch;
import gd.domain.recommendations.dto.GameLinks;
import gd.infrastructure.analytics.Analytics;
import gd.infrastructure.database.QueryPagination;
import gd.infrastructure.error.AlreadyExistsException;
import gd.infrastructure.error.GameDiscoveryException;
import gd.infrastructure.error.NotExistsException;
import gd.infrastructure.log.LogMarker;
import gd.infrastructure.log.LogProducer;
import gd.infrastructure.uid.UID;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/backend", produces = MediaType.APPLICATION_JSON_VALUE)
public class GameDiscoveryAPI {

    // REPOSITORIES
    @Autowired
    private Entities entities;

    @Autowired
    private Recommendations recommendations;

    // SERVICES
    @Autowired
    private Analytics analytics;

    @Autowired
    private gd.infrastructure.error.Error error;

    @Autowired
    private UID uid;

    @Autowired
    private ResponseHelper response;

    // LOGGER
    private static final Logger LOGGER = LogProducer.getLogger(GameDiscoveryAPI.class);

    // DATA
    private static final int SEARCH_PAGINATION_SIZE = 20;
    private static final int RELATED_GAMES_PAGINATION_SIZE = 32;

    // =========================================================================
    // CONCEPTS
    // =========================================================================    
    @RequestMapping(value = "/concepts", method = RequestMethod.GET)
    public ResponseEntity getConceptTypes() {
        // log
        LOGGER.info(LogMarker.API, "Retrieving concepts");

        // process
        Collection<ConceptType> types = entities.getConceptTypes();

        // response
        return response.found(types);
    }

    // =========================================================================
    // CONCEPTS ENTRIES (QUERY)
    // =========================================================================    
    @RequestMapping(value = "/concepts/all", method = RequestMethod.GET)
    public ResponseEntity searchConceptsEntries(
            @RequestParam(value = "query", required = false, defaultValue = "") String query,
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page) {
        // log
        LOGGER.info(LogMarker.API, "Retrieving concept entries | query={}, page={}", query, page);
        analytics.track("Query", "type", "all", "query", query);

        // process
        Collection<ConceptEntry> conceptEntries = entities.getConceptEntries(query, QueryPagination.fromPage(page, SEARCH_PAGINATION_SIZE));

        // response
        return response.found(conceptEntries);
    }

    @RequestMapping(value = "/concepts/{typeUid}", method = RequestMethod.GET)
    public ResponseEntity searchConceptsEntriesByConcept(
            @PathVariable(value = "typeUid") String typeUid,
            @RequestParam(value = "query", required = false, defaultValue = "") String query,
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page) {
        // log
        LOGGER.info(LogMarker.API, "Retrieving concept entries | typeUid={}, query={}, page={}", typeUid, query, page);
        analytics.track("Query", "type", typeUid, "query", query);

        // parse
        ConceptType type = entities.getConceptTypeThrowExceptionIfNotFound(typeUid);

        // process
        Collection<ConceptEntry> conceptEntries;
        if (StringUtils.isBlank(query)) {
            conceptEntries = entities.getConceptEntries(type, QueryPagination.fromPage(page, SEARCH_PAGINATION_SIZE));
        } else {
            conceptEntries = entities.getConceptEntries(type, query, QueryPagination.fromPage(page, SEARCH_PAGINATION_SIZE));
        }

        // response
        return response.found(conceptEntries);
    }

    @RequestMapping(value = "/concepts/all/{entryUid:.+}", method = RequestMethod.GET)
    public ResponseEntity getConceptEntry(
            @PathVariable(value = "entryUid") String entryUid,
            @RequestParam(value = "fetchRelationships", required = false, defaultValue = "true") boolean fetchRelationships) {
        // process
        return getConceptEntry(ConceptEntry.class.getSimpleName(), entryUid, fetchRelationships);
    }

    @RequestMapping(value = "/concepts/{typeUid}/{entryUid:.+}", method = RequestMethod.GET)
    public ResponseEntity getConceptEntry(
            @PathVariable(value = "typeUid") String typeUid,
            @PathVariable(value = "entryUid") String entryUid,
            @RequestParam(value = "fetchRelationships", required = false, defaultValue = "true") boolean fetchRelationships) {
        // log
        LOGGER.info(LogMarker.API, "Retrieving concept entry | typeUid={}, entryUid={}, fetchRelationshps={}", typeUid, entryUid, fetchRelationships);

        // parse
        ConceptType type = entities.getConceptTypeThrowExceptionIfNotFound(typeUid);

        // process
        entryUid = uid.fixDeprecatedFormatIfNecessary(entryUid);
        Optional<ConceptEntry> opConceptEntry = entities.getConceptEntry(type, entryUid, fetchRelationships);

        // response
        if (opConceptEntry.isPresent()) {
            return response.found(opConceptEntry.get());
        } else {
            return response.notFound();
        }
    }

    // =========================================================================
    // GAMES
    // =========================================================================
    @RequestMapping(value = "/games/{gameUid:.+}", method = RequestMethod.GET)
    public ResponseEntity getGame(@PathVariable(value = "gameUid") String gameUid) {
        return getConceptEntry("game", gameUid, true);
    }

    @RequestMapping(value = "/games/{gameUid:.+}/links", method = RequestMethod.GET)
    public ResponseEntity getGameLinks(
            @PathVariable(value = "gameUid") String gameUid) throws IOException {
        LOGGER.info(LogMarker.API, "Retrieving game links | typeUid=game, gameUid={}", gameUid);

        // process
        gameUid = uid.fixDeprecatedFormatIfNecessary(gameUid);
        GameLinks gameLinks = recommendations.getGameLinks(gameUid);

        // response
        return response.found(gameLinks);
    }

    @RequestMapping(value = "/games/related", method = RequestMethod.GET)
    public ResponseEntity getGames(
            @RequestParam(value = "uids", required = false) String[] conceptEntryUids,
            @RequestParam(value = "categories", required = false) String[] categoryUids,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page) {
        LOGGER.info(LogMarker.API, "Retrieving related games | entryUids={}, categoryUids={}", Arrays.toString(conceptEntryUids), Arrays.toString(categoryUids));

        if (conceptEntryUids == null) {
            conceptEntryUids = new String[0];
        }
        if (categoryUids == null) {
            categoryUids = new String[0];
        }

        // process
        conceptEntryUids = Arrays.stream(conceptEntryUids).map(entryUid -> uid.fixDeprecatedFormatIfNecessary(entryUid)).toArray(size -> new String[size]);
        GameDiscoverySearch gameQuery = recommendations.getRelatedGames(conceptEntryUids, categoryUids, QueryPagination.fromPage(page, RELATED_GAMES_PAGINATION_SIZE));

        // response
        return response.found(gameQuery);
    }

    // =========================================================================
    // EXCEPTION HANDLING
    // =========================================================================
    // APPLICATION EXCEPTIONS
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity handleGameDiscoveryException(Exception e) {
        // log expected exception
        if (e instanceof GameDiscoveryException || e instanceof ServletRequestBindingException) {
            LOGGER.warn(LogMarker.API, "Handled exception | ex={}", e.getMessage());
        }
        // handle applications exceptions        
        if (e instanceof NotExistsException) {
            return response.badRequest(e.getMessage());
        }
        if (e instanceof AlreadyExistsException) {
            return response.alreadyExists(e.getMessage());
        }

        // framework exceptions
        if (e instanceof HttpMessageNotReadableException) {
            return response.badRequest(e.getMessage());
        }
        if (e instanceof ServletRequestBindingException) {
            return response.badRequest(e.getMessage());
        }

        // unexpected exception
        return handleUnexpectedException(e);
    }

    // UNKNOWN EXCEPTIONS
    private ResponseEntity handleUnexpectedException(Exception e) {
        LOGGER.error(LogMarker.API, "Unhandled exception | ex={}", e.getMessage(), e);
        error.track(e);
        return response.internalServerError();
    }

}
