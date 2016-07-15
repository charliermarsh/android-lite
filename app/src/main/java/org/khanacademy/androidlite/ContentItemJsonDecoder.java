package org.khanacademy.androidlite;

import org.json.JSONException;
import org.json.JSONObject;

public final class ContentItemJsonDecoder implements JsonDecoder<JSONObject, ContentItem> {
    private static class JsonKeys {
        private static final String READABLE_ID = "readableId";
        private static final String TITLE = "title";
        private static final String YOUTUBE_ID = "youtubeId";
    }

    @Override
    public ContentItem fromJson(final JSONObject jsonObject) throws JSONException {
        return new ContentItem(
                jsonObject.getString(JsonKeys.READABLE_ID),
                jsonObject.getString(JsonKeys.TITLE),
                jsonObject.getString(JsonKeys.YOUTUBE_ID)
        );
    }
}
