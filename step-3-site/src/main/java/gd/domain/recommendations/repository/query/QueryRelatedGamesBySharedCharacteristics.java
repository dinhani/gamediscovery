package gd.domain.recommendations.repository.query;

import com.google.common.collect.Lists;
import gd.domain.recommendations.entity.GraphSearch;
import gd.domain.recommendations.repository.cache.GraphCache;
import gd.infrastructure.log.LogMarker;
import gd.infrastructure.log.LogProducer;
import gd.infrastructure.steriotype.GDQuery;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

@GDQuery
@Scope(value = "prototype")
public class QueryRelatedGamesBySharedCharacteristics implements QueryRelatedGames {

    // =====================================================================
    // SERVICES
    // =====================================================================
    @Autowired
    private GraphCache graph;

    @Autowired
    private QueryRelatedGamesByCharacteristics byCharacteristics;

    private static final Logger LOGGER = LogProducer.getLogger(QueryRelatedGamesExecutor.class);

    // =====================================================================
    // EXECUTION
    // =====================================================================    
    @Override
    public Collection<GraphSearch> execute(String[] searchUids, String[] categoriesToUse) {

        // 1) get games and characteristics to query
        List<String> gameUids = Arrays.stream(searchUids).filter(searchUid -> StringUtils.startsWith(searchUid, "game-")).collect(Collectors.toList());

        // 2) join the characteristics of all games into a single collection
        List<String> characteristcs = Lists.newArrayList();
        for (String gameUid : gameUids) {
            characteristcs.addAll(graph.getGraph().neighborsOf(gameUid));
        }

        // 3) filter only the characteristics shared by all games
        List<String> sharedCharacteristcs = characteristcs
                // sum characteristics
                .stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                // keep only characteristics common for all search uids
                .entrySet()
                .stream()
                .filter(e -> e.getValue() == gameUids.size())
                .map(e -> e.getKey())
                .collect(Collectors.toList());

        // 4) keep only certain types of characteristics
//        sharedCharacteristcs = sharedCharacteristcs.stream()
//                .filter(characteristic -> characteristic.startsWith("genre-")
//                        || characteristic.startsWith("platform-"))
//                .collect(Collectors.toList());
        // 5) query characteristics using only the shared characteristics
        LOGGER.trace(LogMarker.DB, "searchUids: {}, sharedCharacteristics: {}", gameUids, sharedCharacteristcs);
        return byCharacteristics.execute(sharedCharacteristcs.toArray(new String[sharedCharacteristcs.size()]), categoriesToUse);
    }
}
