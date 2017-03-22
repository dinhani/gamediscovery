package gd.infrastructure.asset.provider.dropbox;

import gd.infrastructure.asset.AssetProvider;
import gd.infrastructure.steriotype.GDService;

@GDService
public class DropboxAssetProvider implements AssetProvider {

    private static final String URL = "http://dl.dropboxusercontent.com/u/70844675/game-discovery/";

    @Override
    public String generateUrl(String filename) {
        return URL + filename;
    }

}
