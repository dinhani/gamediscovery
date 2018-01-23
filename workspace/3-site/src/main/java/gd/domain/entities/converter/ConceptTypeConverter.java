package gd.domain.entities.converter;

import com.google.common.collect.Lists;
import gd.domain.entities.entity.ConceptType;
import gd.domain.entities.entity.ConceptEntry;
import gd.domain.entities.annotation.ConceptTypeDescriptor;
import gd.domain.entities.annotation.RelationshipDescriptor;
import gd.domain.entities.entity.RelationshipInfo;
import gd.infrastructure.log.LogMarker;
import gd.infrastructure.log.LogProducer;
import gd.infrastructure.reflection.Reflection;
import gd.infrastructure.steriotype.GDConverter;
import gd.infrastructure.text.Text;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.metadata.ClassInfo;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

@GDConverter
public class ConceptTypeConverter {

    @Autowired
    private Reflection reflection;

    @Autowired
    private Text text;

    private static final Logger LOGGER = LogProducer.getLogger(ConceptTypeConverter.class);

    public Collection<ConceptType> fromClasses(Collection<Class<? extends ConceptEntry>> conceptClasses) {

        List<ConceptType> conceptTypes = Lists.newArrayListWithExpectedSize(conceptClasses.size());

        // parse concept type basic info
        for (Class<? extends ConceptEntry> conceptClass : conceptClasses) {
            LOGGER.trace(LogMarker.DOMAIN, "Parsing basic info | class={}", conceptClass.getName());

            // get descriptor
            ConceptTypeDescriptor descriptor = conceptClass.getAnnotation(ConceptTypeDescriptor.class);

            // uid
            ConceptType type = new ConceptType();
            type.setUid(conceptClass.getSimpleName().toLowerCase());
            type.setTargetClass(conceptClass);

            // name
            String name = StringUtils.isNotBlank(descriptor.name()) ? descriptor.name() : StringUtils.join(StringUtils.splitByCharacterTypeCamelCase(conceptClass.getSimpleName()), " ");
            type.setName(name);
            type.setPluralName(text.pluralize(type.getName()));

            // area
            type.setArea(descriptor.area());

            // icon
            type.setIcon(conceptClass.getAnnotation(ConceptTypeDescriptor.class).icon());

            // filter
            type.setUseAsFilter(conceptClass.getAnnotation(ConceptTypeDescriptor.class).useAsFilter());
            type.setChecked(conceptClass.getAnnotation(ConceptTypeDescriptor.class).useAsFilterActiveByDefault());

            conceptTypes.add(type);
        }

        // parse concept type relationships
        for (ConceptType type : conceptTypes) {
            LOGGER.trace(LogMarker.DOMAIN, "Parsing relationship info | class={}", type.getTargetClass().getName());

            ClassInfo classInfo = reflection.getClassInfo(type.getTargetClass());

            for (Field field : FieldUtils.getFieldsListWithAnnotation(type.getTargetClass(), Relationship.class)) {
                // target type
                ConceptType targetType = getConceptType(conceptTypes, classInfo.relationshipFieldByName(field.getName()).getTypeDescriptor());

                // set info
                RelationshipInfo relInfo = new RelationshipInfo();
                relInfo.setUid(field.getName());
                relInfo.setName(field.getAnnotation(RelationshipDescriptor.class).name());
                relInfo.setRelevance(field.getAnnotation(RelationshipDescriptor.class).relevance());
                relInfo.setType(field.getAnnotation(Relationship.class).type());
                relInfo.setDirection(field.getAnnotation(Relationship.class).direction());
                relInfo.setTargetType(targetType);

                type.getRelationships().put(relInfo.getUid(), relInfo);
            }
        }

        return conceptTypes;
    }

    // =========================================================================
    // HELPER
    // =========================================================================
    private ConceptType getConceptType(final Collection<ConceptType> types, String typeDescriptor) {
        String[] parts = StringUtils.split(typeDescriptor, '/');
        String className = parts[parts.length - 1].replace(";", "");

        return types.stream().filter((t) -> t.getTargetClass().getSimpleName().equalsIgnoreCase(className)).findFirst().get();
    }

}
