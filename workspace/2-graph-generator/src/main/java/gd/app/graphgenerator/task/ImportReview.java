package gd.app.graphgenerator.task;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import gd.domain.entities.Node;
import gd.domain.entities.entity.ConceptEntry;
import gd.domain.entities.entity.industry.Review;
import gd.infrastructure.steriotype.GDTask;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.math.NumberUtils;

@GDTask
public class ImportReview extends ImportTask {

    @Override
    public Class<? extends ConceptEntry> getTargetClass() {
        return Review.class;
    }

    @Override
    public Collection<Node> getNodesToCreate() {
        List<String> reviewers = Lists.newArrayList("IGN", "GameSpot");

        Set<Node> scores = Sets.newHashSet();

        // iterate reviewers
        for (String reviewer : reviewers) {
            // from 0.0 to 10.9, ignores values upper 10
            for (int intPart = 0; intPart <= 10; intPart++) {
                for (int decimalPart = 0; decimalPart <= 9; decimalPart++) {
                    String scoreId = reviewer.toLowerCase() + "_" + intPart + "." + decimalPart;
                    String scoreName = reviewer + " - " + intPart + "." + decimalPart;
                    double score = NumberUtils.toDouble(intPart + "." + decimalPart);

                    if (score <= 10.0) {
                        Node node = Node.withPredefinedIdAndName(getTargetClass(), scoreId, scoreName);
                        scores.add(node);
                    }
                }
            }
        }

        return scores;
    }

}
