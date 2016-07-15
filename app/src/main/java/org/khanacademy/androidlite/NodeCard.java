package org.khanacademy.androidlite;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.RippleDrawable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

public class NodeCard extends FrameLayout {
    private TextView mTopicNameView;

    public NodeCard(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTopicNameView = (TextView) findViewById(R.id.topic_name);
    }

    public void updateData(final Node node) {
        final ColorPalette colorPalette = ColorPalette.forDomain(node.domain, getContext());

        // Set the topic title.
        mTopicNameView.setText(node.title);
        mTopicNameView.setTextColor(colorPalette.text);

        final RippleDrawable rippleDrawable = (RippleDrawable) getBackground();
        rippleDrawable.setColor(new ColorStateList(
                new int[][] { new int[]{}
                        },
                new int[]
                        {
                                colorPalette.pressed
                        }
        ));

    }
}
