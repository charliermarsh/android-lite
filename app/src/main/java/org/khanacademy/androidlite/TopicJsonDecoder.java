package org.khanacademy.androidlite;

import org.json.JSONException;
import org.json.JSONObject;

public final class TopicJsonDecoder implements JsonDecoder<JSONObject, Topic> {
    private static class JsonKeys {
        private static final String SLUG = "slug";
        private static final String TITLE = "title";
        private static final String DOMAIN_SLUG = "domainSlug";
    }

    @Override
    public Topic fromJson(final JSONObject jsonObject) throws JSONException {
        System.out.println("DOMAIN " + jsonObject.getString(JsonKeys.DOMAIN_SLUG));
        return new Topic(
                jsonObject.getString(JsonKeys.SLUG),
                jsonObject.getString(JsonKeys.TITLE),
                Domain.getDomainBySlug(jsonObject.getString(JsonKeys.DOMAIN_SLUG))
        );
    }
}
