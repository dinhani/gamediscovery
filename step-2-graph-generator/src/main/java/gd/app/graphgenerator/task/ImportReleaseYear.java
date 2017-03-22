package gd.app.graphgenerator.task;

import com.google.common.collect.Sets;
import gd.domain.entities.Node;
import gd.domain.entities.entity.ConceptEntry;
import gd.domain.entities.entity.industry.ReleaseYear;
import gd.infrastructure.steriotype.GDTask;
import java.util.Collection;
import java.util.Set;

@GDTask
public class ImportReleaseYear extends ImportTask {

    public static final Node CANCELLED = Node.withPredefinedIdAndName(ReleaseYear.class, "cancelled", "Cancelled");

    @Override
    public Class<? extends ConceptEntry> getTargetClass() {
        return ReleaseYear.class;
    }

    @Override
    public Collection<Node> getNodesToCreate() {
        // release years
        Set<Node> years = Sets.newHashSet();
        for (int year = 1970; year <= 2020; year++) {
            years.add(new Node(year + "", year + ""));
        }

        // predefined
        years.add(CANCELLED);

        return years;
    }

}
