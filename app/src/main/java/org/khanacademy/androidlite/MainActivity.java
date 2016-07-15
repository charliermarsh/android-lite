package org.khanacademy.androidlite;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.http.HttpResponseCache;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainActivity extends Activity implements FragmentManager.OnBackStackChangedListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getActionBar().setDisplayUseLogoEnabled(true);
        getActionBar().setDisplayShowTitleEnabled(false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            enableHttpResponseCache();
        }

        // Respond to changes in the backstack, so as to dynamically enable and disable the up
        // navigation button.
        getFragmentManager().addOnBackStackChangedListener(this);

        // Assemble the data.
        final Bundle arguments = new Bundle();
        arguments.putString(NodesFragment.Keys.PARENT_SLUG, null);

        // Build the fragment.
        final NodesFragment fragment = new NodesFragment();
        fragment.setArguments(arguments);

        // Add it to the activity.
        final FragmentManager fragmentManager = getFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.activity_main, fragment).addToBackStack("root");
        fragmentTransaction.commit();
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

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                final FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.popBackStack();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackStackChanged() {
        final boolean allowUpNavigation = getFragmentManager().getBackStackEntryCount() > 1;
        getActionBar().setDisplayHomeAsUpEnabled(allowUpNavigation);
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
