package gd.domain.entities.factory;

import gd.domain.entities.entity.ConceptEntry;
import gd.domain.entities.repository.query.QueryConceptType;
import gd.infrastructure.di.DIService;
import gd.infrastructure.uid.UID;
import org.apache.commons.lang3.StringUtils;

public class ConceptEntryFactory {

    private static UID UID;
    private static QueryConceptType QUERY_TYPE;

    // =========================================================================
    // TYPE
    // =========================================================================
    public static ConceptEntry createFromType(String typeUid) {
        initServices();

        Class<? extends ConceptEntry> type = QUERY_TYPE.execute(typeUid).get().getTargetClass();
        return createFromType(type);
    }

    public static <T extends ConceptEntry> T createFromType(Class<T> type) {
        try {
            return type.newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
            return null;
        }
    }

    // =========================================================================
    // TYPE, ID AND NAME
    // =========================================================================
    public static <T extends ConceptEntry> T createFromId(Class<T> type, String id) {
        return create(type, id, "");
    }

    public static <T extends ConceptEntry> T createFromName(Class<T> type, String name) {
        return create(type, "", name);
    }

    public static <T extends ConceptEntry> T createFromIdAndName(Class<T> type, String id, String name) {
        return create(type, id, name);
    }

    // =========================================================================
    // FACTORY
    // =========================================================================
    private static <T extends ConceptEntry> T create(Class<T> type, String id, String name) {
        initServices();

        // type
        T entry = createFromType(type);

        // id only
        if (StringUtils.isNotBlank(id) && StringUtils.isBlank(name)) {
            entry.setUid(UID.generateUid(type, id));
            entry.setName(id);
        }
        // name only
        if (StringUtils.isBlank(id) && StringUtils.isNotBlank(name)) {
            entry.setUid(UID.generateUid(type, name));
            entry.setName(name);
        }
        // id and name
        if (StringUtils.isNotBlank(id) && StringUtils.isNotBlank(name)) {
            entry.setUid(UID.generateUid(type, id));
            entry.setName(name);
        }
        return entry;

    }

    // =========================================================================
    // LAZY INITIALIZATION
    // =========================================================================
    private static void initServices() {
        if (UID == null) {
            UID = DIService.getBean(UID.class);
        }
        if (QUERY_TYPE == null) {
            QUERY_TYPE = DIService.getBean(QueryConceptType.class);
        }
    }

}
