package org.khanacademy.androidlite;

import static org.khanacademy.androidlite.Utils.checkNotNull;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.net.http.HttpResponseCache;
import android.os.Build;
import android.os.Bundle;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            final ActionBar actionBar = checkNotNull(getActionBar());
            actionBar.setDisplayUseLogoEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }

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

        final HttpResponseCache cache;
        try {
            cache = HttpResponseCache.install(httpCacheDir, httpCacheSize);
        } catch (final IOException e) {
            throw new RuntimeException("Failed to install cache", e);
        }

        // Report on the cache status periodically.
        final Logger cacheLogger = Logger.getLogger(HttpResponseCache.class.getSimpleName());
        final Timer reportingTimer = new Timer();
        final long initialDelayMs = 1000;
        final long periodMs = 3000;
        reportingTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                cacheLogger.log(
                        Level.INFO,
                        String.format(
                                Locale.ENGLISH,
                                "Requests=%d, Hits=%d, Networks=%d -- Total size=%d bytes",
                                cache.getRequestCount(),
                                cache.getHitCount(),
                                cache.getNetworkCount(),
                                cache.size()
                        )
                );
            }
        }, initialDelayMs, periodMs);
    }
}
