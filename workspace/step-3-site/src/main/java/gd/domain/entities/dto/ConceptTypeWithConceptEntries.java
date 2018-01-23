package gd.domain.entities.dto;

import com.google.common.collect.Lists;
import gd.domain.entities.entity.ConceptEntry;
import gd.domain.entities.entity.ConceptType;
import java.util.Collection;

public class ConceptTypeWithConceptEntries implements Comparable<ConceptTypeWithConceptEntries> {

    private final ConceptType type;
    private final Collection<ConceptEntry> entries = Lists.newArrayList();

    // =========================================================================
    // CONSTRUCTORS
    // =========================================================================
    public ConceptTypeWithConceptEntries(ConceptType type) {
        this.type = type;
    }

    // =========================================================================
    // GETTERS
    // =========================================================================
    public ConceptType getType() {
        return type;
    }

    public Collection<ConceptEntry> getEntries() {
        return entries;
    }

    @Override
    public int compareTo(ConceptTypeWithConceptEntries o) {
        return this.getType().getName().compareTo(o.getType().getName());
    }

}
