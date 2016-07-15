package org.khanacademy.androidlite;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by charliemarsh on 7/14/16.
 */

public final class UrlBuilder {
    private static final String baseUrl = "http://10.0.2.2:5000";

    public static URL forPath(final String path) {
        try {
            return new URL(baseUrl + path);
        } catch (final MalformedURLException e) {
            throw new RuntimeException("Invalid extension: " + path);
        }
    }
}
