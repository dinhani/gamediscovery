package gd.infrastructure.network;

import java.net.URISyntaxException;
import org.apache.commons.lang3.StringUtils;

public class URI {

    private final java.net.URI uri;
    private final String uriAsString;

    private final String user;
    private final String password;

    public URI(String uriAsString) throws URISyntaxException {
        // parse URI
        this.uriAsString = uriAsString;
        uri = new java.net.URI(uriAsString);

        // parse user info
        if (uri.getUserInfo() != null) {
            String[] userInfo = StringUtils.split(uri.getUserInfo(), ':');

            if (userInfo.length == 2) {
                user = userInfo[0];
                password = userInfo[1];
            } else {
                user = "";
                password = "";
            }
        } else {
            user = "";
            password = "";
        }
    }

    // =========================================================================
    // GETTERS
    // =========================================================================
    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getConnectionHost() {
        return uri.getScheme() + "://" + uri.getHost() + ":" + uri.getPort();
    }

    @Override
    public String toString() {
        return uriAsString;
    }

}
