package org.khanacademy.androidlite;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.FrameLayout;

public class ErrorView extends FrameLayout {
    private Button mRetryButton;

    public ErrorView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mRetryButton = (Button) findViewById(R.id.retry_button);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            mRetryButton.setOnTouchListener((view, motionEvent) -> {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        view.setAlpha(0.5f);
                        break;

                    default:
                        view.setAlpha(1f);
                        break;
                }
                return false;
            });
        }
    }

    public void updateData(final Action0 onRetry) {
        mRetryButton.setOnClickListener(view -> {
            onRetry.call();
        });
    }
}
