package gd.infrastructure.reflection;

import gd.infrastructure.log.LogProducer;
import gd.infrastructure.steriotype.GDService;
import java.util.Collection;
import org.neo4j.ogm.metadata.ClassInfo;
import org.neo4j.ogm.session.SessionFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

@GDService
public class Reflection {

    @Autowired
    private SessionFactory sessionFactory;

    private static final Logger LOGGER = LogProducer.getLogger(Reflection.class);

    public <T> Collection<Class<? extends T>> getClasses(String packageName, Class<T> type) {
        return ReflectionsProducer.reflection(packageName).getSubTypesOf(type);
    }

    public ClassInfo getClassInfo(Class clazz) {
        return getClassInfo(clazz.getName());
    }

    public ClassInfo getClassInfo(String className) {
        return sessionFactory.metaData().classInfo(className);
    }

}
