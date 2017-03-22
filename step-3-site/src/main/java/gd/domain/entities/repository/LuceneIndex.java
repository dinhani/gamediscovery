package gd.domain.entities.repository;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import gd.domain.entities.entity.ConceptEntry;
import gd.domain.entities.entity.industry.Game;
import gd.domain.entities.entity.industry.Platform;
import gd.domain.entities.factory.ConceptEntryFactory;
import gd.infrastructure.di.DIService;
import gd.infrastructure.log.LogProducer;
import gd.infrastructure.steriotype.GDService;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import javax.annotation.PostConstruct;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.NoLockFactory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.lucene.document.Document;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;

@GDService
public class LuceneIndex {

    private static final Sort BOOST_SORT = new Sort(new SortField("boost", SortField.Type.FLOAT, true));
    private static final Set<String> FIELDS_TO_LOAD = Sets.newHashSet("uid", "name", "alias", "image", "platforms", "category", "boost");

    // SERVICES
    private IndexSearcher searcher;
    private StandardAnalyzer analyzer;
    private QueryParser parser;

    @Autowired
    private DIService di;

    @Autowired
    private gd.infrastructure.error.Error error;

    private static final org.slf4j.Logger LOGGER = LogProducer.getLogger(LuceneIndex.class);

    // =========================================================================
    // INITIALIZATION
    // =========================================================================
    @PostConstruct
    public void init() {
        try {
            // read index
            Path luceneInClasspath = Paths.get(DIService.getResource("classpath:lucene").getURI());
            Directory index = new SimpleFSDirectory(luceneInClasspath, NoLockFactory.INSTANCE);
            IndexReader reader = DirectoryReader.open(index);
            searcher = new IndexSearcher(reader);

            // build analyser
            analyzer = new StandardAnalyzer();

            // build query parsers
            parser = new QueryParser("search", analyzer);

        } catch (IOException e) {
            LOGGER.error("Error reading Lucene index | message={}", e.getMessage(), e);
            System.exit(2);
        }
    }

    public Collection<ConceptEntry> query(String query, int limit) {
        // prepare
        query = QueryParser.escape(query);
        query = String.format("search:%s", query);

        // execute
        return doQuery(query, limit);
    }

    public Collection<ConceptEntry> query(String query, String category, int limit) {
        // prepare
        query = QueryParser.escape(query);
        if (StringUtils.isBlank(query)) {
            query = String.format("category:%s", category);
        } else {
            query = String.format("category:%s AND search:%s", category, query);
        }

        // execute
        return doQuery(query, limit);
    }

    // =========================================================================
    // EXECUTE LUCENE QUERY
    // =========================================================================
    // RETURNS A MAP WITH (UID => RESULT POSITION)
    private Collection<ConceptEntry> doQuery(String query, int limit) {
        try {
            // parse query
            Query luceneQuery = parser.parse(query);

            // do query
            ScoreDoc[] docs;
            if (query.contains("search:")) {
                docs = searcher.search(luceneQuery, limit).scoreDocs;
            } else {
                docs = searcher.search(luceneQuery, limit, BOOST_SORT).scoreDocs;
            }

            // extract ids         
            Collection<ConceptEntry> entries = Lists.newArrayListWithExpectedSize(docs.length);
            for (int i = 0; i < docs.length; ++i) {
                Document d = searcher.doc(docs[i].doc, FIELDS_TO_LOAD);
                entries.add(toConceptEntry(d));
            }
            return entries;
        } catch (ParseException | IOException e) {
            LOGGER.error("Error parsing Lucene query | message={}", e.getMessage(), e);
            error.track(e);

            return Collections.EMPTY_LIST;
        }
    }

    // =========================================================================
    // CONVERSOR
    // =========================================================================
    private ConceptEntry toConceptEntry(Document document) {
        ConceptEntry entry = ConceptEntryFactory.createFromType(document.get("category"));
        entry.setUid(document.get("uid"));
        entry.setName(document.get("name"));
        entry.setImage(document.get("image"));
        entry.setAlias(document.get("alias"));

        // additional fields for game
        if (entry instanceof Game) {
            Game game = (Game) entry;

            // platforms
            String platformsStr = document.get("platforms");
            if (platformsStr != null) {
                String[] platformNames = StringUtils.split(platformsStr, ';');
                for (String platformName : platformNames) {
                    Platform platform = new Platform();
                    platform.setName(platformName);
                    game.getPlatforms().add(platform);
                }
            }
        }

        return entry;
    }

}
