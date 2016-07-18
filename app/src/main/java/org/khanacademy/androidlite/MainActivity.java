package org.khanacademy.androidlite;

import android.annotation.TargetApi;
import android.app.Activity;
import android.net.http.HttpResponseCache;
import android.os.Build;
import android.os.Bundle;

import java.io.File;
import java.io.IOException;

public final class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            enableHttpResponseCache();
        }

        // Launch the root topic intent.
        startActivity(IntentCreator.forAllSubjects(this));
        finish();
    }

    @Override
    protected void onStop() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            final HttpResponseCache cache = HttpResponseCache.getInstalled();
            if (cache != null) {
                cache.flush();
            }
        }

        super.onStop();
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private void enableHttpResponseCache() {
        final long httpCacheSize = 10 * 1024 * 1024;
        final File httpCacheDir = new File(getCacheDir(), "http");

        try {
            HttpResponseCache.install(httpCacheDir, httpCacheSize);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }
}
