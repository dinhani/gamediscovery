package gd.app.importer.repository;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import gd.app.importer.model.Node;
import gd.infrastructure.serialization.Json;
import java.util.List;
import java.util.Set;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

public class DataImporterJDBCQuery {

    // SERVICES
    private final DataImporterJDBC jdbc;
    private final Json json;

    // DATA
    private final List<String> attributesKeys = Lists.newArrayList();
    private final List<String> attributesValues = Lists.newArrayList();
    private final List<String> categories = Lists.newArrayList();
    private String regex = "";
    private boolean forGame;

    public DataImporterJDBCQuery(DataImporterJDBC jdbc, Json json) {
        this.jdbc = jdbc;
        this.json = json;
    }

    // =========================================================================
    // PARAMETERS
    // =========================================================================
    public DataImporterJDBCQuery attributesKeys(String... keysToAdd) {
        CollectionUtils.addAll(attributesKeys, keysToAdd);
        return this;
    }

    public DataImporterJDBCQuery attributesValues(String... valuesToAdd) {
        CollectionUtils.addAll(attributesValues, valuesToAdd);
        return this;
    }

    public DataImporterJDBCQuery categories(String... categoriesToAdd) {
        CollectionUtils.addAll(categories, categoriesToAdd);
        return this;
    }

    public DataImporterJDBCQuery regex(String regex) {
        this.regex = regex;
        return this;
    }

    public DataImporterJDBCQuery forGame() {
        this.forGame = true;
        return this;
    }

    // =========================================================================
    // QUERIES
    // =========================================================================
    public Set<Node> findDBPediaArticles() {
        String sql = ""
                + "SELECT dac.id_wikipedia as id, dac.id_wikipedia as name, dac.categories::text as dbpediaCategories "
                + "\n FROM dbpedia_article_categories dac "
                + "\n WHERE " + whereForRegexOrCategories("dac.id", "dac.categories");
        return jdbc.executeQuery(sql);
    }

    public Set<Node> findDBPediaCategories() {
        String sql = ""
                + "SELECT dcc.id_wikipedia as id, dcc.id_wikipedia as name, dcc.categories::text as dbpediaCategories "
                + "\n FROM dbpedia_category_categories dcc "
                + "\n WHERE " + whereForRegexOrCategories("dcc.id_wikipedia", "dcc.categories");
        return jdbc.executeQuery(sql);
    }

    public Set<Node> findWikidataEntities() {
        String sql = ""
                + "SELECT DISTINCT ON (id) COALESCE(NULLIF(we.id_wikipedia,''), we.id) as id, "
                + "we.name as name, "
                + "we.link as image, "
                + "we.id_wikidata as wikidataId, "
                + "we.attributes::text as wikidataAttributes ";

        if (forGame) {
            sql += ""
                    + "\n , daa.attributes::text as dbpediaAttributes, dac.categories::text as dbpediaCategories, "
                    + "\n waen.word_count::text as wikipediaEnWordCount, "
                    + "\n waen.attributes::text as wikipediaAttributes, "
                    + "\n wapt.word_count::text as wikipediaPtWordCount, "
                    + "\n wc.attributes::text as wikipediaCooperativeAttributes, "
                    + "\n wg.id_wikipedia is not null as wikipediaGoty,"
                    + "\n esrb.categories::text as esrbCategories, esrb.classification as esrbClassification,"
                    + "\n ign.score as ignScore,"
                    + "\n gs.score as gamespotScore,"
                    + "\n gb.word_count::text as giantbombWordCount, gb.aliases::text as giantbombAliases, "
                    + "\n lb.attributes::text as launchboxAttributes, lb.summary as launchboxSummary, lb.attributes->'video' as launchboxVideo, "
                    + "\n hltb.attributes::text as hltbAttributes, "
                    + "\n mg.attributes::text as mobygamesAttributes, "
                    + "\n tgdb.attributes::text as thegamesdbAttributes ";                   
        }
        sql += ""
                + "\n FROM wikidata_entities we ";

        if (forGame) {
            sql += ""
                    + "\n LEFT JOIN dbpedia_article_categories dac USING (id_wikipedia) "
                    + "\n LEFT JOIN dbpedia_article_attributes daa USING (id_wikipedia) "
                    + "\n LEFT JOIN wikipedia_articles_en waen USING (id_wikipedia) "
                    + "\n LEFT JOIN wikipedia_articles_pt wapt USING (id_wikipedia) "
                    + "\n LEFT JOIN wikipedia_cooperative wc USING (id_wikipedia) "
                    + "\n LEFT JOIN wikipedia_goty wg USING (id_wikipedia) "
                    + "\n LEFT JOIN esrb_games esrb ON we.id = esrb.id "
                    + "\n LEFT JOIN ign_games ign ON we.id = ign.id "
                    + "\n LEFT JOIN gamespot_games gs ON we.id = gs.id "
                    + "\n LEFT JOIN giantbomb_games gb ON we.id = gb.id "
                    + "\n LEFT JOIN hltb_games hltb ON we.id = hltb.id "
                    + "\n LEFT JOIN launchbox_games lb ON we.id = lb.id "
                    + "\n LEFT JOIN mobygames_games mg ON we.id = mg.id "
                    + "\n LEFT JOIN thegamesdb_games tgdb ON we.id = tgdb.id ";
        }
        sql += ""
                + "\n WHERE " + whereForAttributes("we.attributes")
                + "\n ORDER BY id";
        
        if(forGame){
            System.out.println(sql);
        }

        return jdbc.executeQuery(sql);
    }

    // =========================================================================
    // WHERE GENERATOR
    // =========================================================================
    private String whereForAttributes(String attributesColumn) {
        List<String> whereClauses = Lists.newArrayList();

        // cartesian of keys and values
        for (String key : attributesKeys) {
            for (String value : attributesValues) {
                Multimap<String, String> filter = ArrayListMultimap.create();
                filter.put(key, value);
                String whereClause = attributesColumn + " @> " + "'" + json.toJson(filter.asMap()) + "'";
                whereClauses.add(whereClause);
            }
        }

        return StringUtils.join(whereClauses, " OR ");
    }

    private String whereForRegexOrCategories(String idColumn, String categoriesColumn) {
        if (StringUtils.isNotBlank(regex)) {
            return whereForRegex(idColumn);
        } else {
            return whereForCategories(categoriesColumn);
        }
    }

    private String whereForRegex(String column) {
        return column + " ~ '" + regex + "'";
    }

    private String whereForCategories(String column) {
        return column + " @> " + "'{" + StringUtils.join(categories, ",") + "}'";
    }

}
