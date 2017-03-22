package gd.domain.recommendations.repository.query;

import gd.domain.recommendations.entity.GraphSearch;
import java.util.Collection;

public interface QueryRelatedGames {

    public Collection<GraphSearch> execute(String[] searchUids, String[] categoriesToUse);
}
