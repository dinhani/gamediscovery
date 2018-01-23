package gd.infrastructure.text;

import gd.infrastructure.log.LogProducer;
import gd.infrastructure.steriotype.GDService;
import org.atteo.evo.inflector.English;
import org.slf4j.Logger;

@GDService
public class Text {

    private static final Logger LOGGER = LogProducer.getLogger(Text.class);

    public String pluralize(String str) {
        if (str == null) {
            LOGGER.warn("Invalid pluralize call with null argment");
            return "";
        }

        String plural = English.plural(str);
        if (plural.endsWith("ses")) {
            plural = plural.replace("ses", "s");
        }
        return plural;
    }
}
