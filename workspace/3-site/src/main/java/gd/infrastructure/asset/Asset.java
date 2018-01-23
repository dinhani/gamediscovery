package gd.infrastructure.asset;

import gd.infrastructure.log.LogProducer;
import gd.infrastructure.steriotype.GDService;
import gd.infrastructure.ui.Image;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

@GDService
public class Asset {

    @Autowired
    private AssetProvider assetProvider;

    private static final Logger LOGGER = LogProducer.getLogger(Asset.class);

    // =========================================================================
    // IMAGE ASSETS
    // =========================================================================
    public String generateImageUrl(String wikipediaLink) {
        // check if null or blank
        if (StringUtils.isBlank(wikipediaLink)) {
            return Image.DEFAULT_SRC;
        }

        // transform wikipedia link into image format
        String imageFilename = wikipediaLink.toLowerCase().replace('\\', '-').replace('/', '-').replace(':', '-').replace(',', '-');
        if (imageFilename.endsWith(".")) {
            imageFilename += "_";
        }
        imageFilename += ".png";

        // generate url
        return assetProvider.generateUrl(imageFilename);
    }

}
