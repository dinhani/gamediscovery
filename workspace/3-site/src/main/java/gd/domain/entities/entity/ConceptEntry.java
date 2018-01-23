package gd.domain.entities.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import gd.domain.entities.annotation.ConceptTypeDescriptor;
import gd.domain.entities.dto.ConceptEntryWithRelationshipInfo;
import gd.domain.entities.repository.query.QueryConceptType;
import gd.infrastructure.asset.Asset;
import gd.infrastructure.di.DIService;
import gd.infrastructure.ui.Icon;
import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;
import java.util.SortedSet;
import jodd.bean.BeanUtil;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.springframework.util.StringUtils;

@NodeEntity
@JsonIdentityInfo(generator = JSOGGenerator.class)
@ConceptTypeDescriptor(area = ConceptTypeArea.INDUSTRY, icon = Icon.GAME)
public abstract class ConceptEntry implements Serializable, Comparable<ConceptEntry> {

    private static final long serialVersionUID = 1L;

    // =========================================================================
    // SERVICES
    // =========================================================================
    private transient Asset asset;

    // =========================================================================
    // DOMAIN DATA (STORED IN DB)
    // =========================================================================
    @GraphId
    private Long id;

    private String uid;

    private String name;

    private String alias;

    private String summary;

    // wikipedia image is used to generate the image id
    @JsonIgnore
    private String image;

    @JsonIgnore
    private String video;

    // =========================================================================
    // OBJECT METHODS
    // =========================================================================
    @Override
    public int compareTo(ConceptEntry o) {
        return this.getName().compareTo(o.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid);
    }

    @Override
    public boolean equals(Object obj) {
        try {
            ConceptEntry other = (ConceptEntry) obj;
            return this.getUid().equals(other.getUid());
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return String.format("entryUid=%s, entryName=\"%s\"", getUid(), getName());
    }

    // =========================================================================
    // PROCESSED GETTERS
    // =========================================================================
    // TYPE
    public ConceptType getType() {
        QueryConceptType queryConceptType = DIService.getBean(QueryConceptType.class);
        return queryConceptType.execute(getClass()).get();
    }

    @JsonIgnore
    public Collection<ConceptEntryWithRelationshipInfo> getRelatedEntriesAndRelationships() {
        Collection<ConceptEntryWithRelationshipInfo> relatedEntries = Lists.newArrayList();

        // iterate relationships for current type
        for (RelationshipInfo relationship : getType().getRelationships().values()) {
            // iterate the entries of a relationship type
            SortedSet<? extends ConceptEntry> setOfRelatedEntries = (SortedSet<? extends ConceptEntry>) BeanUtil.getProperty(this, relationship.getUid());

            for (ConceptEntry relatedEntry : setOfRelatedEntries) {
                double relevanceModifier = RelationshipRelevanceBoost.NORMAL;
                if (setOfRelatedEntries instanceof TreeSetWithAttributes) {
                    relevanceModifier = ((TreeSetWithAttributes) setOfRelatedEntries).getRelevanceModifier(relatedEntry);
                }
                relatedEntries.add(new ConceptEntryWithRelationshipInfo(relationship, relatedEntry, relevanceModifier));
            }
        }
        return relatedEntries;
    }

    // IMAGE
    public String getImageFullPath() {
        initAssetService();
        return asset.generateImageUrl(image);
    }

    // =========================================================================
    // PROCESSED SETTERS
    // =========================================================================
    public void setUid(String uid) {
        this.uid = uid;
        if (name == null) {
            name = uid;
        }
    }

    // =========================================================================
    // INTERNAL CHECKS
    // =========================================================================
    private void initAssetService() {
        if (asset == null) {
            asset = DIService.getBean(Asset.class);
        }
    }

    // =========================================================================
    // PROCESSED GETTERS
    // =========================================================================    
    @JsonIgnore
    public String getNameEscapedForHtml() {
        return StringUtils.replace(name, "'", "&#39;'");
    }

    @JsonIgnore
    public String getNameEscapedForJson() {
        return StringUtils.replace(name, "'", "\\'");
    }

    // =========================================================================
    // GETTERS AND SETTERS
    // =========================================================================
    public Long getId() {
        return id;
    }

    public String getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public String getAlias() {
        return alias;
    }

    public String getSummary() {
        return summary;
    }

    public String getVideo() {
        return video;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setVideo(String video) {
        this.video = video;
    }

}
