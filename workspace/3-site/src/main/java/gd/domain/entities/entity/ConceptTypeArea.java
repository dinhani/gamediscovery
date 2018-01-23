package gd.domain.entities.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.StringUtils;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ConceptTypeArea {

    INDUSTRY("Industry", "Game Industry"),
    GAMEPLAY("Gameplay"),
    PLOT("Plot");

    // =========================================================================
    // DATA
    // =========================================================================
    private final String uid;
    private final String name;

    // =========================================================================
    // CONSTRUCTORS
    // =========================================================================
    private ConceptTypeArea(String uid) {
        this(uid, StringUtils.capitalize(uid));
    }

    private ConceptTypeArea(String uid, String name) {
        this.uid = uid;
        this.name = name;
    }

    // =========================================================================
    // GETTERS
    // =========================================================================
    public String getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

}
