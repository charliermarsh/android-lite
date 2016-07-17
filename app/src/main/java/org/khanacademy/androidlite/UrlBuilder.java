package org.khanacademy.androidlite;

import java.net.MalformedURLException;
import java.net.URL;

public final class UrlBuilder {
    private static final String BASE_URL = "http://10.0.2.2:5000";
    private static final String MP4_DOWNLOAD_URL_TEMPLATE =
            "http://fastly.kastatic.org/KA-youtube-converted/%s.mp4/%s.mp4";

    public static URL forPath(final String path) {
        try {
            return new URL(BASE_URL + path);
        } catch (final MalformedURLException e) {
            throw new RuntimeException("Invalid extension: " + path);
        }
    }

    public static String forYoutubeId(final String youtubeId) {
        return String.format(MP4_DOWNLOAD_URL_TEMPLATE, youtubeId, youtubeId);
    }
}
