package org.khanacademy.androidlite;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.List;

public class NodeListActivity extends Activity {
    private static final int MAX_PRE_FETCHED_TOPICS = 5;

    static final class Keys {
        static final String PARENT_DOMAIN_SLUG = "parentDomainSlug";
        static final String PARENT_SLUG = "parentSlug";
        static final String TITLE = "title";
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_node_list);

        final String title = getIntent().getStringExtra(Keys.TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // If we have full control over the toolbar, show the logo when appropriate, but
            // otherwise hide it in favor of a title.
            final KaToolbar toolbar = (KaToolbar) findViewById(R.id.toolbar);
            toolbar.updateData(title);

            setActionBar(toolbar);
            getActionBar().setDisplayShowTitleEnabled(false);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // Otherwise, set the title as long as we have the ability to do so.
            if (title != null) {
                getActionBar().setTitle(title);
            }
        }

        fetchData();
    }

    private void fetchData() {
        // Fetch the JSON.
        final String parentSlug = getIntent().getStringExtra(Keys.PARENT_SLUG);
        final String path = parentSlug == null ? "/subjects" : "/topic/" + parentSlug;

        JsonFetcher.fetchJsonAsync(
                UrlBuilder.forPath(path),
                jsonObject -> {
                    if (jsonObject != null) {
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
                    } else {
                        onError();
                    }
                }
        );
    }

    private void onNodesFetched(final List<Node> nodes) {
        // Hide the error view.
        final ErrorView errorView = (ErrorView) findViewById(R.id.error_view);
        errorView.setVisibility(View.GONE);

        // Display the nodes.
        final ListView nodesView = (ListView) findViewById(R.id.nodes_view);
        nodesView.setAdapter(
                new NodesAdapter(this, nodes, this::onNodeSelected)
        );

        // Prefetch a few of the child nodes.
        final int numNodesToPrefetch = Math.min(nodes.size(), MAX_PRE_FETCHED_TOPICS);
        for (final Node node : nodes.subList(0, numNodesToPrefetch)) {
            if (node.kind() == Kind.TOPIC) {
                JsonFetcher.prefetchJson(UrlBuilder.forPath("/topic/" + node.slug));
            }
        }
    }

    private void onError() {
        final ErrorView errorView = (ErrorView) findViewById(R.id.error_view);

        // Make the error view visible.
        errorView.setVisibility(View.VISIBLE);

        // Set the retry handler.
        errorView.updateData(this::fetchData);
    }

    private void onNodeSelected(final Node node) {
        switch (node.kind()) {
            case Kind.TOPIC:
                navigateToTopic((Topic) node);
                return;

            case Kind.CONTENT_ITEM:
                navigateToContentItem((ContentItem) node);
                return;
        }

        throw new IllegalArgumentException("Invalid node kind: " + node.kind());
    }

    private void navigateToTopic(final Topic topic) {
        final Intent intent = IntentCreator.forTopic(topic, this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startActivity(
                    intent,
                    ActivityOptions.makeSceneTransitionAnimation(this).toBundle()
            );
        } else {
            startActivity(intent);
        }
    }

    private void navigateToContentItem(final ContentItem contentItem) {
        startActivity(IntentCreator.forContentItem(contentItem, this));
    }
}
