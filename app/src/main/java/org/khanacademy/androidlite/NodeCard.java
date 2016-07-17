package org.khanacademy.androidlite;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

public class NodeCard extends FrameLayout {
    private TextView mNodeNameView;

    public NodeCard(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mNodeNameView = (TextView) findViewById(R.id.node_name);
    }

    public void updateData(final Node node) {
        final ColorPalette colorPalette = ColorPalette.forDomain(node.domain, getContext());

        // Set the topic title.
        mNodeNameView.setText(node.title);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            final RippleDrawable rippleDrawable = (RippleDrawable) getBackground();
            rippleDrawable.setColor(new ColorStateList(
                    new int[][]{
                            new int[]{}
                    },
                    new int[]{
                            colorPalette.pressed
                    }
            ));

            mNodeNameView.setTextColor(colorPalette.text);
        } else {
            final StateListDrawable stateListDrawable = (StateListDrawable) getBackground();
            stateListDrawable.addState(
                    new int[]{android.R.attr.state_pressed},
                    new ColorDrawable(colorPalette.pressed)
            );

            // TODO(charlie): Dynamic text coloration doesn't seem to work on API 13 and below.
            mNodeNameView.setTextColor(new ColorStateList(
                    new int[][]{
                            new int[]{android.R.attr.state_pressed},
                            new int[]{}
                    },
                    new int[] {
                            Color.WHITE,
                            colorPalette.text
                    }
            ));
        }

    }
}
