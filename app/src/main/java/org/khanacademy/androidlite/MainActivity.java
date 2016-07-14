package org.khanacademy.androidlite;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

import java.util.List;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        JsonFetcher.fetchJsonAsync(
                RequestCreator.urlForPath("/subjects"),
                jsonObject -> {
                    // Parse the list of topics.
                    try {
                        final JSONArray topicsJsonArray = jsonObject.getJSONArray("topics");
                        final TopicListJsonDecoder jsonDecoder = new TopicListJsonDecoder();
                        final List<Topic> topics = jsonDecoder.fromJson(topicsJsonArray);

                        onTopicsFetched(topics);
                    } catch (final JSONException e) {
                        throw new RuntimeException(
                                "Failed to find topics: " + jsonObject.toString()
                        );
                    }
                }
        );
    }

    private void onTopicsFetched(final List<Topic> topics) {
        final GridView topicGridView = (GridView) findViewById(R.id.topic_view);
        topicGridView.setAdapter(
                new TopicAdapter(this, R.layout.topic_icon, topics)
        );
    }
}
