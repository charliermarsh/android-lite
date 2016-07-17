package org.khanacademy.androidlite;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import android.widget.Toolbar;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class KaToolbar extends Toolbar {
    private View mLogoView;
    private TextView mTitleView;

    public KaToolbar(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mLogoView = findViewById(R.id.logo);
        mTitleView = (TextView) findViewById(R.id.title);
    }

    public void updateData(final String maybeTitle) {
        if (maybeTitle == null) {
            mLogoView.setVisibility(View.VISIBLE);
            mTitleView.setVisibility(View.GONE);
        } else {
            mLogoView.setVisibility(View.GONE);
            mTitleView.setVisibility(View.VISIBLE);
            mTitleView.setText(maybeTitle);
        }
    }
}
