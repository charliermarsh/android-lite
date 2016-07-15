package org.khanacademy.androidlite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class NodesAdapter extends ArrayAdapter<Node> {
    private final Action1<Node> mNavigationListener;

    public NodesAdapter(final Context context,
                        final List<Node> nodes,
                        final Action1<Node> navigationListener) {
        super(context, R.layout.topic_icon, nodes);

        mNavigationListener = navigationListener;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        final Node node = getItem(position);

        final View outputView;
        if (convertView == null) {
            outputView = LayoutInflater
                    .from(getContext())
                    .inflate(R.layout.topic_icon, parent, false);
        } else {
            outputView = convertView;
        }

        // Add the title.
        final TextView topicNameView = (TextView) outputView.findViewById(R.id.topic_name);
        topicNameView.setText(node.title);

        // Download the thumbnail.
//        final ImageView topicIconView = (ImageView) outputView.findViewById(R.id.topic_icon);
//        ImageDownloader.downloadImage(topicIconView, UrlBuilder.forThumbnail(node.slug));

        // Setup the click listener.
        outputView.setOnClickListener(v -> mNavigationListener.call(node));

        return outputView;
    }
}
