package gd.infrastructure.database.provider.neo4j;

import gd.infrastructure.steriotype.GDFactory;
import org.neo4j.ogm.cypher.BooleanOperator;
import org.neo4j.ogm.cypher.ComparisonOperator;
import org.neo4j.ogm.cypher.Filter;

@GDFactory
public class Neo4JFilterFactory {

    public Filter matches(String property, String value) {
        Filter filter = new Filter(property, value);
        filter.setComparisonOperator(ComparisonOperator.MATCHES);
        filter.setBooleanOperator(BooleanOperator.OR);

        return filter;
    }
}
