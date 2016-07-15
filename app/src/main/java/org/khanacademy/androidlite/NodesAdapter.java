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
        super(context, R.layout.node_card, nodes);

        mNavigationListener = navigationListener;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        final Node node = getItem(position);

        final NodeCard outputView;
        if (convertView == null) {
            outputView = (NodeCard) LayoutInflater
                    .from(getContext())
                    .inflate(R.layout.node_card, parent, false);
        } else {
            outputView = (NodeCard) convertView;
        }

        // Add the title.
        final TextView topicNameView = (TextView) outputView.findViewById(R.id.topic_name);
        topicNameView.setText(node.title);

        outputView.setOnClickListener(v -> mNavigationListener.call(node));

        outputView.updateData(node);

        return outputView;
    }
}
