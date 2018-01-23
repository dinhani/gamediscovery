package gd.infrastructure.uid;

import com.google.common.base.Preconditions;
import gd.domain.entities.entity.ConceptEntry;
import gd.domain.entities.entity.industry.Game;
import gd.infrastructure.error.ErrorMessages;
import gd.infrastructure.steriotype.GDService;
import org.apache.commons.lang3.StringUtils;

@GDService
public class UID {

    private static final String DASH_COMPACTATION_REGEX = "\\-+";
    private static final String SPACE_COMPACTATION_REGEX = "\\s+";
    private static final String SPACE_REGEX = "\\s";
    private static final String SYMBOL_REGEX = "[^\\w\\s-]";
    private static final String PARENTHESIS_REGEX = "_\\(.+\\)";

    // =========================================================================
    // UID
    // =========================================================================
    public String generateUid(Class<? extends ConceptEntry> type, String name) {
        // 0) validate input
        Preconditions.checkArgument(name != null, ErrorMessages.NOT_BLANK, "name");
        Preconditions.checkArgument(StringUtils.isNotBlank(name), ErrorMessages.NOT_BLANK, "name");

        // =====================================================================
        // 1) TYPE PART
        // 1.1) lower case
        String typePart = type.getSimpleName().toLowerCase();

        // =====================================================================
        // 2) NAME PART
        // 2.1) lowercase
        String namePart = name.toLowerCase();

        // 2.2) trim
        namePart = namePart.trim();

        // 2.3) remove accents
        namePart = StringUtils.stripAccents(namePart);

        // 2.4) remove parenthesis
        namePart = StringUtils.replacePattern(namePart, PARENTHESIS_REGEX, "");

        // 2.5) remove special symbols
        namePart = namePart.replaceAll(SYMBOL_REGEX, "");

        // 2.5) compact spaces
        namePart = namePart.replaceAll(SPACE_COMPACTATION_REGEX, " ");

        // 2.6) transform spaces into dashes
        namePart = namePart.replaceAll(SPACE_REGEX, "_");

        // 2.7) transform underscores into dashes
        namePart = StringUtils.replace(namePart, "_", "-");

        // 2.8) compact dashes
        namePart = namePart.replaceAll(DASH_COMPACTATION_REGEX, "-");

        // 2.9) remove leading dash
        if (namePart.startsWith("-")) {
            namePart = namePart.substring(1);
        }

        // =====================================================================
        // 3) JOIN TYPE AND NAME
        return typePart + "-" + namePart;
    }

    public String generateUidForceUnique(Class<? extends ConceptEntry> type, String name) {
        String uid = generateUid(type, name);
        uid = uid + "-" + System.currentTimeMillis();

        return uid;
    }
}
