package gd.app.importer;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Maps;
import gd.app.importer.step.AbstractStep;
import gd.domain.entities.entity.ConceptType;
import gd.domain.entities.entity.RelationshipInfo;
import gd.domain.entities.entity.RelationshipRelevance;
import gd.domain.entities.repository.query.QueryConceptTypes;
import gd.infrastructure.di.DIService;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public class DataReporter extends AbstractStep {

    // DATA
    private final Map<String, RelationshipRelevance> knownRelationships = Maps.newHashMap(); // description, relevance

    // SERVICES
    private final ApplicationContext ctx;

    @Autowired
    private final QueryConceptTypes queryConceptTypes;

    // =========================================================================
    // INITIALIZATION
    // =========================================================================
    public static void main(String[] args) throws Exception {
        DataReporter importer = new DataReporter();
        importer.run();
    }

    public DataReporter() {
        ctx = initSpringContext();
        queryConceptTypes = DIService.getBean(QueryConceptTypes.class);
    }

    // =========================================================================
    // EXECUTION
    // =========================================================================
    public void run() throws SQLException, IOException {
        Collection<ConceptType> types = queryConceptTypes.execute();
        for (ConceptType type : types) {

            Collection<RelationshipInfo> relationships = type.getRelationships().values().stream().sorted(new RelationshipInfoComparator().reversed()).collect(Collectors.toList());

            System.out.println("----------------------------------------");
            for (RelationshipInfo relationship : relationships) {
                // relationship description
                String relationshipDescription;

                // check relationship direction to generate description
                if (relationship.isIncoming()) {
                    relationshipDescription = relationship.getTargetType().getName() + " " + relationship.getType() + " " + type.getName();
                } else {
                    relationshipDescription = type.getName() + " " + relationship.getType() + " " + relationship.getTargetType().getName();
                }

                // check for inconsistences between mappings
                RelationshipRelevance relevance = knownRelationships.get(relationshipDescription);
                if (relevance != null) { // it will be null on first pass, but not in the second
                    // check mismatch
                    if (relevance != relationship.getRelevance()) {
                        System.out.println("Mismatch for: " + relationshipDescription + " with " + relevance + " and " + relationship.getRelevance());
                    }
                }
                // store relationship for next comparison
                knownRelationships.put(relationshipDescription, relationship.getRelevance());

                // output table data
                System.out.format("%-18s%-18s%-14s | %s%n",
                        type.getName(),
                        relationship.getTargetType().getName(),
                        relationship.getRelevance(),
                        relationshipDescription);
            }
        }
    }

    private static class RelationshipInfoComparator implements Comparator<RelationshipInfo> {

        @Override
        public int compare(RelationshipInfo o1, RelationshipInfo o2) {
            return ComparisonChain.start()
                    .compare(o1.getRelevance().getWeight(), o2.getRelevance().getWeight())
                    .compare(o2.getName(), o1.getName())
                    .result();
        }
    }

}
