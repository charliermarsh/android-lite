package org.khanacademy.androidlite;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.MenuItem;

public class MainActivity extends Activity implements FragmentManager.OnBackStackChangedListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
}
