package gd.domain.entities.annotation;

import gd.domain.entities.entity.RelationshipRelevance;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RelationshipDescriptor {

    public String name();

    public RelationshipRelevance relevance();
}
