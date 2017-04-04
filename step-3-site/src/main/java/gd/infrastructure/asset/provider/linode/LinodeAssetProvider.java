package gd.infrastructure.asset.provider.linode;

import gd.infrastructure.asset.AssetProvider;
import gd.infrastructure.steriotype.GDService;

@GDService
public class LinodeAssetProvider implements AssetProvider {

    private static final String URL = "http://192.155.82.172:8080/";

    @Override
    public String generateUrl(String filename) {
        return URL + filename;
    }

}
