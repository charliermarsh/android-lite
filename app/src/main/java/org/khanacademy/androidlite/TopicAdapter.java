package org.khanacademy.androidlite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class TopicAdapter extends ArrayAdapter<Topic> {
    public TopicAdapter(final Context context, final int resource, final List<Topic> topics) {
        super(context, resource, topics);
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        final Topic topic = getItem(position);

        final View outputView;
        if (convertView == null) {
            outputView = LayoutInflater
                    .from(getContext())
                    .inflate(R.layout.topic_icon, parent, false);
        } else {
            outputView = convertView;
        }

        final TextView topicNameView = (TextView) outputView.findViewById(R.id.topic_name);
        topicNameView.setText(topic.title);

        return outputView;
    }
}
