package gd.infrastructure.error;

import java.util.Arrays;
import org.apache.commons.lang3.StringUtils;

public class NotExistsException extends GameDiscoveryException {

    public NotExistsException(Class c, String... uids) {
        this(c.getSimpleName(), uids);
    }

    public NotExistsException(String type, String... uids) {
        super(StringUtils.join(StringUtils.splitByCharacterTypeCamelCase(type), " ") + " with unique ID \"" + Arrays.toString(uids) + "\" does not exist");
    }

}
