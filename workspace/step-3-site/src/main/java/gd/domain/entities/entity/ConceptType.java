package gd.domain.entities.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Maps;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import gd.infrastructure.ui.Icon;
import java.io.Serializable;
import java.util.Map;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class ConceptType implements Serializable, Comparable<ConceptType> {

    private static final long serialVersionUID = 1L;

    private String uid = "";
    private String name = "";
    private String pluralName = "";

    private ConceptTypeArea area;
    private Icon icon;
    private boolean useAsFilter = false;
    private boolean checked = false;

    @JsonIgnore
    private Class<? extends ConceptEntry> targetClass;

    private Map<String, RelationshipInfo> relationships = Maps.newLinkedHashMap();

    // =========================================================================
    // OBJECT METHODS
    // =========================================================================
    @Override
    public int compareTo(ConceptType o) {
        if (this.name.equals("Game")) {
            return -1;
        }
        if (o.name.equals("Game")) {
            return +1;
        }
        return name.compareTo(o.name);
    }

    @Override
    public String toString() {
        return String.format("typeUid=%s, typeName=%s", uid, name);
    }

    // =========================================================================
    // GETTERS AND SETTERS
    // =========================================================================
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public String getPluralName() {
        return pluralName;
    }

    public void setPluralName(String pluralName) {
        this.pluralName = pluralName;
    }

    @JsonIgnore
    public ConceptTypeArea getArea() {
        return area;
    }

    public void setArea(ConceptTypeArea area) {
        this.area = area;
    }

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public Class<? extends ConceptEntry> getTargetClass() {
        return targetClass;
    }

    public void setTargetClass(Class<? extends ConceptEntry> targetClass) {
        this.targetClass = targetClass;
    }

    public Map<String, RelationshipInfo> getRelationships() {
        return relationships;
    }

    public void setRelationships(Map<String, RelationshipInfo> relationships) {
        this.relationships = relationships;
    }

    public boolean isUseAsFilter() {
        return useAsFilter;
    }

    public void setUseAsFilter(boolean useAsFilter) {
        this.useAsFilter = useAsFilter;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

}
