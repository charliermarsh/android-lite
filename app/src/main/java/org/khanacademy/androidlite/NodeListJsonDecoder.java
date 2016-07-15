package org.khanacademy.androidlite;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public final class NodeListJsonDecoder implements JsonDecoder<JSONArray, List<Node>> {
    private static class JsonKeys {
        private static final String KIND = "kind";
    }

    private static final TopicJsonDecoder TOPIC_DECODER = new TopicJsonDecoder();
    private static final ContentItemJsonDecoder CONTENT_ITEM_DECODER = new ContentItemJsonDecoder();

    @Override
    public List<Node> fromJson(final JSONArray jsonArray) throws JSONException {
        final List<Node> arrayList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            final JSONObject jsonObject = jsonArray.getJSONObject(i);
            final String kind = jsonObject.getString(JsonKeys.KIND);

            if (kind.equals("Topic")) {
                arrayList.add(
                        TOPIC_DECODER.fromJson(jsonArray.getJSONObject(i))
                );
            } else {
                arrayList.add(
                        CONTENT_ITEM_DECODER.fromJson(jsonArray.getJSONObject(i))
                );
            }
        }

        return arrayList;
    }
}
