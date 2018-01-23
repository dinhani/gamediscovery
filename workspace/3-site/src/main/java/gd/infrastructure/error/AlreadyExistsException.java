package gd.infrastructure.error;

import org.apache.commons.lang3.StringUtils;

public class AlreadyExistsException extends GameDiscoveryException {

    public AlreadyExistsException(Class c, Object uid) {
        this(c.getSimpleName(), uid == null ? "null" : uid.toString());
    }

    public AlreadyExistsException(String type, String uid) {
        super(StringUtils.join(StringUtils.splitByCharacterTypeCamelCase(type), " ") + " with unique ID \"" + uid + "\" already exists");
    }
}
