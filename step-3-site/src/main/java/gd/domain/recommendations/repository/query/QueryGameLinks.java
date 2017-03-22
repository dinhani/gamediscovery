package gd.domain.recommendations.repository.query;

import com.google.common.collect.Lists;
import com.mashape.unirest.http.Unirest;
import gd.domain.recommendations.dto.GameLinks;
import gd.infrastructure.log.LogProducer;
import gd.infrastructure.steriotype.GDQuery;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;

@GDQuery
public class QueryGameLinks {

    private static final Logger LOGGER = LogProducer.getLogger(QueryGameLinks.class);

    public GameLinks execute(String gameUid) {
        // generate url
        String gameplaySearchTerm = generateGameplaySearchTerm(gameUid);
        String gameSearchTerm = generateGameSearchTerm(gameUid);

        String googleImagesLink = generateGoogleImagesLink(gameplaySearchTerm);
        String youtubeLink = generateYoutubeLink(gameplaySearchTerm);
        String hltbLink = generateHowLongToBeatLink(gameSearchTerm);
        Collection<String> images = generateImagesUrls(googleImagesLink);

        return new GameLinks(images, googleImagesLink, youtubeLink, hltbLink);
    }

    // =========================================================================
    // SEARCH TERM GENERATION
    // =========================================================================
    private String generateGameplaySearchTerm(String gameUid) {
        return StringUtils.join(gameUid.split("[_-]"), "+") + "+gameplay";
    }

    private String generateGameSearchTerm(String gameUid) {
        String[] parts = gameUid.split("[_-]");
        parts = Arrays.copyOfRange(parts, 1, parts.length);

        return StringUtils.join(parts, "+");
    }

    // =========================================================================
    // LINK GENERATION
    // =========================================================================
    private String generateGoogleImagesLink(String searchTerm) {
        return String.format("http://www.google.com/search?q=%s&tbm=isch", searchTerm);
    }

    private String generateYoutubeLink(String searchTerm) {
        return String.format("http://www.youtube.com/results?search_query=%s", searchTerm);
    }

    private String generateHowLongToBeatLink(String searchTerm) {
        return String.format("https://howlongtobeat.com/?q=%s", searchTerm);
    }

    // =========================================================================
    // IMAGES
    // =========================================================================
    private Collection<String> generateImagesUrls(String googleImagesLink) {
        try {
            // open connection
            LOGGER.trace("Retrieving game images | url={}", googleImagesLink);
            String bodyResponse = Unirest.get(googleImagesLink).asString().getBody();

            // parse response
            Document doc = Jsoup.parse(bodyResponse);
            List<String> imageUrls = doc.select("img").stream().map(img -> img.attr("src")).collect(Collectors.toList());

            // check if images were returned
            if (imageUrls.size() > 0) {
                return imageUrls.subList(1, imageUrls.size());
            } else {
                LOGGER.warn("No Google Images returned | url={}", googleImagesLink);
                return Collections.EMPTY_LIST;
            }
        } catch (Exception e) {
            LOGGER.error("Error retrieving game images | url={}, ex={}", googleImagesLink, e.getMessage(), e);
            return Lists.newArrayList();
        }
    }

}
