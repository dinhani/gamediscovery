package gd.domain.entities.entity;

import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.TreeSet;

public class TreeSetWithAttributes<E> extends TreeSet<E> {

    private Map<E, Double> relevanceModifiers = Collections.EMPTY_MAP;

    public static <E extends Comparable> TreeSet<E> create() {
        return new TreeSetWithAttributes<>();
    }

    public TreeSetWithAttributes() {
        super();
    }

    public TreeSetWithAttributes(Collection<? extends E> c) {
        super(c);
    }

    public void setRelevanceModifier(E relatedEntry, double relevanceModifier) {
        if (relevanceModifiers == Collections.EMPTY_MAP) {
            relevanceModifiers = Maps.newHashMap();
        }
        relevanceModifiers.put(relatedEntry, relevanceModifier);
    }

    public Double getRelevanceModifier(E relatedEntry) {
        Double modifier = relevanceModifiers.get(relatedEntry);
        if (modifier == null) {
            modifier = RelationshipRelevanceBoost.NORMAL;
        }
        return modifier;
    }

}
