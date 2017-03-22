package gd.domain.entities.annotation;

import gd.infrastructure.ui.Icon;
import gd.domain.entities.entity.ConceptTypeArea;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ConceptTypeDescriptor {

    public String name() default "";

    public ConceptTypeArea area();

    public Icon icon();

    public boolean useAsFilter() default false;

    public boolean useAsFilterActiveByDefault() default false;
}
