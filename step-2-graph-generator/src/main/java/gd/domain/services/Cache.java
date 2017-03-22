package gd.domain.services;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import gd.domain.entities.Node;
import gd.domain.entities.entity.ConceptEntry;
import gd.domain.entities.entity.TreeSetWithAttributes;
import gd.infrastructure.steriotype.GDService;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;

@GDService
public class Cache {

    // type, id, value
    private final Map<Class<? extends ConceptEntry>, Map<String, String>> idCache = Maps.newHashMap(); // key = id_wikipedia
    private final Map<Class<? extends ConceptEntry>, Map<String, String>> nameCache = Maps.newHashMap(); // key = name
    private final Map<Class<? extends ConceptEntry>, Map<String, String>> wikidataCache = Maps.newHashMap(); // key = id_wikidata

    // =========================================================================
    // POPULATE
    // =========================================================================
    public void add(Class<? extends ConceptEntry> type, Collection<Node> nodes) {
        for (Node node : nodes) {
            add(type, node);
        }
    }

    public void add(Class<? extends ConceptEntry> type, Node node) {
        // id cache - main id
        if (!idCache.containsKey(type)) {
            idCache.put(type, Maps.newHashMap());
        }
        idCache.get(type).put(node.getId(), node.getGeneratedId());

        // id cache - alternative ids
        for (String alternativeId : node.getAlternativeIds()) {
            idCache.get(type).put(alternativeId, node.getGeneratedId());
        }

        // name cache
        if (!nameCache.containsKey(type)) {
            nameCache.put(type, Maps.newHashMap());
        }
        nameCache.get(type).put(node.getName(), node.getGeneratedId());

        // wikidata cache
        if (StringUtils.isNotBlank(node.getWikidataId())) {
            if (!wikidataCache.containsKey(type)) {
                wikidataCache.put(type, Maps.newHashMap());
            }
            wikidataCache.get(type).put(node.getWikidataId(), node.getGeneratedId());
        }
    }

    // =========================================================================
    // QUERY
    // =========================================================================
    public <T extends ConceptEntry> SortedSet<T> findEntriesById(Class<T> type, String id) {
        return findEntriesByIds(type, Lists.newArrayList(id));
    }

    public <T extends ConceptEntry> SortedSet<T> findEntriesByIds(Class<T> type, Collection<String> ids) {
        return getEntriesFromCache(type, ids, idCache);
    }

    public <T extends ConceptEntry> SortedSet<T> findEntriesByName(Class<T> type, String name) {
        return findEntriesByNames(type, Lists.newArrayList(name));
    }

    public <T extends ConceptEntry> SortedSet<T> findEntriesByNames(Class<T> type, Collection<String> names) {
        return getEntriesFromCache(type, names, nameCache);
    }

    public <T extends ConceptEntry> SortedSet<T> findEntriesByWikidataIds(Class<T> type, Collection<String> wikidataIds) {
        return getEntriesFromCache(type, wikidataIds, wikidataCache);
    }

    // =========================================================================
    // HELPER
    // =========================================================================
    private <T extends ConceptEntry> SortedSet<T> getEntriesFromCache(Class<T> type, Collection<String> ids, Map<Class<? extends ConceptEntry>, Map<String, String>> cache) {
        Set<T> tempSet = ids.parallelStream()
                // get generated id
                .map(id -> cache.containsKey(type) ? cache.get(type).get(id) : null)
                // remove not found (null)
                .filter(generatedUid -> generatedUid != null)
                // generate entry
                .map((String generatedUid) -> {
                    try {
                        T entry = type.newInstance();
                        entry.setUid(generatedUid);
                        return entry;
                    } catch (InstantiationException | IllegalAccessException ex) {
                        ex.printStackTrace();
                        return null;
                    }
                })
                // generate a hashset because cannot generate a sortedset
                .collect(Collectors.toSet());

        return new TreeSetWithAttributes<>(tempSet);
    }
}
