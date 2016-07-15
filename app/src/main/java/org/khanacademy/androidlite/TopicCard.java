package org.khanacademy.androidlite;

import android.content.Context;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

public class TopicCard extends FrameLayout {
    private TextView mTopicNameView;

    public TopicCard(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTopicNameView = (TextView) findViewById(R.id.topic_name);
    }

    public void updateData(final Topic topic) {
        // Set the background color based on the domain.
        final ColorPalette colorPalette = ColorPalette.forDomain(topic.domain, getContext());

        getBackground().setColorFilter(
                colorPalette.light,
                PorterDuff.Mode.SRC
        );

        // Set the topic title.
        mTopicNameView.setText(topic.title);
        mTopicNameView.setTextColor(colorPalette.text);
    }
}
