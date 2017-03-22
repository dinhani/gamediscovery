package gd.domain.entities;

import com.google.common.base.Preconditions;
import gd.domain.entities.entity.ConceptEntry;
import gd.domain.entities.entity.ConceptType;
import gd.domain.entities.repository.query.QueryConceptEntryExists;
import gd.domain.entities.repository.query.QueryConceptEntriesByQuery;
import gd.domain.entities.repository.query.QueryConceptEntriesByType;
import gd.domain.entities.repository.query.QueryConceptEntriesByTypeAndQuery;
import gd.domain.entities.repository.query.QueryConceptEntriesByUIDs;
import gd.domain.entities.repository.query.QueryConceptEntry;
import gd.domain.entities.repository.query.QueryConceptType;
import gd.domain.entities.repository.query.QueryConceptTypes;
import gd.infrastructure.error.NotExistsException;
import gd.infrastructure.database.QueryPagination;
import gd.infrastructure.error.ErrorMessages;
import gd.infrastructure.log.LogMarker;
import gd.infrastructure.log.LogProducer;
import gd.infrastructure.steriotype.GDRepository;
import java.util.Optional;
import java.util.Collection;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

@GDRepository
public class Entities {

    // LOGGER
    private static final Logger LOGGER = LogProducer.getLogger(Entities.class);

    // =========================================================================
    // DATABASE ACTIONS (QUERIES AND CHECKS)
    // =========================================================================
    @Autowired
    private QueryConceptTypes queryConceptTypes;

    @Autowired
    private QueryConceptType queryConceptType;

    @Autowired
    private QueryConceptEntriesByType queryConceptEntriesByType;

    @Autowired
    private QueryConceptEntriesByTypeAndQuery queryConceptEntriesByTypeAndQuery;

    @Autowired
    private QueryConceptEntriesByQuery queryConceptEntriesByQuery;

    @Autowired
    private QueryConceptEntriesByUIDs queryConceptEntriesByUIDs;

    @Autowired
    private QueryConceptEntry queryConceptEntry;

    @Autowired
    private QueryConceptEntryExists existConceptEntry;

    // =========================================================================
    // LIST QUERIES
    // =========================================================================
    public Collection<ConceptType> getConceptTypes() {
        LOGGER.debug(LogMarker.DOMAIN, "Retrieving concept types");
        Collection<ConceptType> conceptTypes = queryConceptTypes.execute();

        return conceptTypes;
    }

    public Collection<ConceptEntry> getConceptEntries(String query, QueryPagination pagination) {
        LOGGER.debug(LogMarker.DOMAIN, "Retrieving concept entries | query={}, {}", query, pagination);
        Preconditions.checkArgument(query != null, ErrorMessages.NOT_NULL, "query");

        return queryConceptEntriesByQuery.execute(query, pagination).getElements();
    }

    public Collection<ConceptEntry> getConceptEntries(ConceptType type, QueryPagination pagination) {
        LOGGER.debug(LogMarker.DOMAIN, "Retrieving concept entries | {}, {}", type, pagination);
        Preconditions.checkArgument(type != null, ErrorMessages.NOT_NULL, "type");

        return queryConceptEntriesByType.execute(type, pagination).getElements();
    }

    public Collection<ConceptEntry> getConceptEntries(ConceptType type, String query, QueryPagination pagination) {
        LOGGER.debug(LogMarker.DOMAIN, "Retrieving concept entries | {}, query={}, {}", type, query, pagination);
        Preconditions.checkArgument(type != null, ErrorMessages.NOT_NULL, "type");
        Preconditions.checkArgument(query != null, ErrorMessages.NOT_NULL, "query");

        return queryConceptEntriesByTypeAndQuery.execute(type, query, pagination).getElements();
    }

    public Collection<ConceptEntry> getConceptEntries(Collection<String> entryUids) {
        LOGGER.debug(LogMarker.DOMAIN, "Retrieving concept entries | entryUids={}", entryUids);
        return queryConceptEntriesByUIDs.execute(entryUids).getElements();
    }

    // =========================================================================
    // DETAILS QUERIES
    // =========================================================================
    public ConceptType getConceptTypeThrowExceptionIfNotFound(Class<? extends ConceptEntry> clazz) {
        return getConceptTypeThrowExceptionIfNotFound(clazz.getSimpleName());
    }

    public ConceptType getConceptTypeThrowExceptionIfNotFound(String typeUid) {
        Optional<ConceptType> opConcept = getConceptType(typeUid);
        if (opConcept.isPresent()) {
            return opConcept.get();
        } else {
            throw new NotExistsException(ConceptEntry.class, typeUid);
        }
    }

    public Optional<ConceptType> getConceptType(Class<? extends ConceptEntry> clazz) {
        LOGGER.debug(LogMarker.DOMAIN, "Retrieving concept type | class={}", clazz);
        return queryConceptType.execute(clazz);
    }

    public Optional<ConceptType> getConceptType(String typeUid) {
        LOGGER.debug(LogMarker.DOMAIN, "Retrieving concept type | typeUid={}", typeUid);
        return queryConceptType.execute(typeUid);
    }

    public ConceptEntry getConceptEntryThrowExceptionIfNotFound(ConceptType type, String entryUid, boolean fetchRelationships) {
        Optional<ConceptEntry> opConceptEntry = getConceptEntry(type, entryUid, fetchRelationships);
        if (opConceptEntry.isPresent()) {
            return opConceptEntry.get();
        } else {
            throw new NotExistsException(ConceptEntry.class, entryUid);
        }
    }

    public Optional<ConceptEntry> getConceptEntry(Class<? extends ConceptEntry> type, String entryUid, boolean fetchRelationships) {
        return getConceptEntry(getConceptType(type).get(), entryUid, fetchRelationships);
    }

    public Optional<ConceptEntry> getConceptEntry(ConceptType type, String entryUid, boolean fetchRelationships) {
        LOGGER.debug(LogMarker.DOMAIN, "Retrieving concept entry | {}, entryUid={}, fetchRelationshps={}", type, entryUid, fetchRelationships);
        Optional<ConceptEntry> opEntry = queryConceptEntry.execute(type, entryUid, fetchRelationships);
        if (opEntry.isPresent()) {
            LOGGER.trace(LogMarker.DOMAIN, "Retrieved concept entry | {}, entryUid={}, fetchRelationshps={}", type, entryUid, fetchRelationships);
        } else {
            LOGGER.warn(LogMarker.DOMAIN, "Cannot retrieve concept entry | {}, entryUid={}, fetchRelationshps={}", type, entryUid, fetchRelationships);
        }

        return opEntry;
    }

    // =========================================================================
    // SPECIAL QUERIES
    // =========================================================================
    // =========================================================================
    // EXISTS
    // =========================================================================
    public boolean existConceptEntry(ConceptType type, String entryUid) {
        LOGGER.debug(LogMarker.DOMAIN, "Checking existence of concept type | {}, entryUid={}", type, entryUid);

        boolean exists = existConceptEntry.execute(type, entryUid);
        return exists;
    }

}
