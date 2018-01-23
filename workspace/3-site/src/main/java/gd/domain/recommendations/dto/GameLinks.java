package gd.domain.recommendations.dto;

import java.util.Collection;

public class GameLinks {

    private final Collection<String> images;
    private final String imagesLink;
    private final String youtubeLink;
    private String hltbLink;

    // =========================================================================
    // CONSTRUCTOR
    // =========================================================================
    public GameLinks(Collection<String> images, String imagesLink, String youtubeLink, String hltbLink) {
        this.images = images;
        this.imagesLink = imagesLink;
        this.youtubeLink = youtubeLink;
        this.hltbLink = hltbLink;
    }

    // =========================================================================
    // GETTERS
    // =========================================================================
    public Collection<String> getImages() {
        return images;
    }

    public String getImagesLink() {
        return imagesLink;
    }

    public String getYoutubeLink() {
        return youtubeLink;
    }

    public String getHltbLink() {
        return hltbLink;
    }

    public void setHltbLink(String hltbLink) {
        this.hltbLink = hltbLink;
    }

}
