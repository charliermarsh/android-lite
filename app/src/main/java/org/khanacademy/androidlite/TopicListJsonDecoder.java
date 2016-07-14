package org.khanacademy.androidlite;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public final class TopicListJsonDecoder implements JsonDecoder<JSONArray, List<Topic>> {
    private static final TopicJsonDecoder TOPIC_DECODER = new TopicJsonDecoder();

    @Override
    public List<Topic> fromJson(final JSONArray jsonArray) throws JSONException {
        final List<Topic> arrayList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            arrayList.add(
                    TOPIC_DECODER.fromJson(jsonArray.getJSONObject(i))
            );
        }

        return arrayList;
    }
}
