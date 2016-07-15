package org.khanacademy.androidlite;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.List;

public class NodesFragment extends Fragment {
    static final class Keys {
        static final String PARENT_SLUG = "parentSlug";
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final String parentSlug = getArguments().getString(Keys.PARENT_SLUG);
        final String path = parentSlug == null ? "/subjects" : "/topic/" + parentSlug;

        JsonFetcher.fetchJsonAsync(
                UrlBuilder.forPath(path),
                jsonObject -> {
                    // Parse the list of topics.
                    try {
                        final JSONArray topicsJsonArray = jsonObject.getJSONArray("topics");
                        final NodeListJsonDecoder jsonDecoder = new NodeListJsonDecoder();
                        final List<Node> nodes = jsonDecoder.fromJson(topicsJsonArray);

                        onNodesFetched(nodes);
                    } catch (final JSONException e) {
                        throw new RuntimeException(
                                "Failed to find children of topic: " + jsonObject.toString()
                        );
                    }
                }
        );

//        getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater,
                             final ViewGroup container,
                             final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_nodes, container, false);
    }

    private void onNodesFetched(final List<Node> nodes) {
        final GridView topicGridView = (GridView) getView().findViewById(R.id.topic_view);
        topicGridView.setAdapter(
                new NodesAdapter(getContext(), nodes, this::onNodeSelected)
        );
    }

    private void onNodeSelected(final Node node) {
        switch (node.kind()) {
            case TOPIC:
                navigateToTopic((Topic) node);
                return;

            case CONTENT_ITEM:
                navigateToContentItem((ContentItem) node);
                return;
        }

        throw new IllegalArgumentException("Invalid node kind: " + node.kind());
    }

    private void navigateToTopic(final Topic topic) {
        // Assemble the data.
        final Bundle arguments = new Bundle();
        arguments.putString(NodesFragment.Keys.PARENT_SLUG, topic.slug);

        // Build the fragment.
        final NodesFragment fragment = new NodesFragment();
        fragment.setArguments(arguments);

        // Add it to the activity.
        final FragmentManager fragmentManager = getFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_topics, fragment).addToBackStack(topic.slug);
        fragmentTransaction.commit();
    }

    private void navigateToContentItem(final ContentItem contentItem) {
        // Assemble the data.
        final Bundle arguments = new Bundle();
        arguments.putString(VideoFragment.Keys.TITLE, contentItem.title);
        arguments.putString(VideoFragment.Keys.YOUTUBE_ID, contentItem.youtubeId);

        // Build the fragment.
        final VideoFragment fragment = new VideoFragment();
        fragment.setArguments(arguments);

        // Add it to the activity.
        final FragmentManager fragmentManager = getFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_topics, fragment).addToBackStack(contentItem.slug);
        fragmentTransaction.commit();
    }
}
