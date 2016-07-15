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
import android.widget.ListView;

import java.util.List;

public class NodesFragment extends Fragment {
    private static final int MAX_PREFETCHED_TOPICS = 5;

    static final class Keys {
        static final String PARENT_DOMAIN_SLUG = "parentDomainSlug";
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
                                "Failed to find children at path: " + path, e
                        );
                    }
                }
        );
    }

    @Override
    public View onCreateView(final LayoutInflater inflater,
                             final ViewGroup container,
                             final Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_nodes, container, false);

        // Color the background, if appropriate.
//        final String parentDomainSlug = getArguments().getString(Keys.PARENT_DOMAIN_SLUG);
//        if (parentDomainSlug != null) {
//            final Domain parentDomain = Domain.getDomainBySlug(parentDomainSlug);
//            final ColorPalette parentDomainPalette = ColorPalette.forDomain(
//                    parentDomain,
//                    getContext()
//            );
//            rootView.setBackgroundColor(parentDomainPalette.dark);
//        }

        return rootView;
    }

    private void onNodesFetched(final List<Node> nodes) {
        final ListView nodesView = (ListView) getView().findViewById(R.id.nodes_view);
        nodesView.setAdapter(
                new NodesAdapter(getActivity(), nodes, this::onNodeSelected)
        );

        // Prefetch a few of the child nodes.
        final int numNodesToPrefetch = Math.min(nodes.size(), MAX_PREFETCHED_TOPICS);
        for (final Node node : nodes.subList(0, numNodesToPrefetch)) {
            if (node.kind() == Kind.TOPIC) {
                JsonFetcher.prefetchJson(UrlBuilder.forPath("/topic/" + node.slug));
            }
        }
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
        arguments.putString(Keys.PARENT_SLUG, topic.slug);
        arguments.putString(Keys.PARENT_DOMAIN_SLUG, topic.domain.slug);

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
