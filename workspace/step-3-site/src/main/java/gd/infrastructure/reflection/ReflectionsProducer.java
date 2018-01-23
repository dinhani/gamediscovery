package gd.infrastructure.reflection;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;

public class ReflectionsProducer {

    public static Reflections reflection(String packageName) {
        return new Reflections(packageName, new SubTypesScanner(false), new TypeAnnotationsScanner());
    }
}
