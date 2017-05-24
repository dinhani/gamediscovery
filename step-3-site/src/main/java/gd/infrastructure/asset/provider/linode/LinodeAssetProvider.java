package gd.infrastructure.asset.provider.linode;

import gd.infrastructure.asset.AssetProvider;
import gd.infrastructure.steriotype.GDService;

@GDService
public class LinodeAssetProvider implements AssetProvider {

    private static final String URL = "/images/";

    @Override
    public String generateUrl(String filename) {
        return URL + filename;
    }

}
