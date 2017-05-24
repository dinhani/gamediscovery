package gd.infrastructure.database;

import org.neo4j.ogm.cypher.query.SortOrder;
import org.neo4j.ogm.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public abstract class AbstractDatabaseCommand {

    // =========================================================================
    // NEO4J
    // =========================================================================
    @Autowired
    protected Session neo4jSession;

    protected SortOrder sorting = new SortOrder().add(SortOrder.Direction.ASC, "name");

    // =========================================================================
    // HELPER FUNCTIONS
    // =========================================================================
    protected String getQueryRegex(String query) {
        String[] queryParts = query.split(" ");

        String regex = "";
        for (String queryPart : queryParts) {
            regex += String.format("(?=%s)", getQueryParam(queryPart));
        }
        regex += ".*";

        return regex;
    }

    protected String getQueryParam(String param) {
        return "(?i).*" + param + ".*";
    }

}
