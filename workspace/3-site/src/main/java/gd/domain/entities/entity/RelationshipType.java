package gd.domain.entities.entity;

/**
 * The possible relationship types between two concept entries
 *
 * @author Renato
 */
public class RelationshipType {

    private RelationshipType() {
    }

    // BUSINESS RELATIONSHIPS
    public static final String AWARDS = "AWARDS";
    public static final String CLASSIFIES = "CLASSIFIES";
    public static final String DEVELOPS = "DEVELOPS";
    public static final String PUBLISHES = "PUBLISHES";
    public static final String OWNS = "OWNS";
    public static final String SELLS = "SELLS";
    public static final String WORKS = "WORKS";

    // GAMING CONTENT RELATIONSHIPS    
    public static final String CONTAINS = "CONTAINS";
    public static final String IS = "IS";
    public static final String IS_SET_IN = "IS_SET_IN";
    public static final String FEATURES = "FEATURES";
    public static final String USES = "USES";

    // GAMING TECH RELATIONSHIPS
    public static final String HAS_TECH = "HAS_TECH";

    // GAMING HUMAN RELATIONSHIPS
    public static final String ALLIES = "ALLIES";
    public static final String OPPOSES = "OPPOSES";

}
