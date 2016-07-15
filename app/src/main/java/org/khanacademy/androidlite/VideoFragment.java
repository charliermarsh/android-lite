package org.khanacademy.androidlite;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class VideoFragment extends Fragment {
    static final class Keys {
        static final String TITLE = "title";
        static final String YOUTUBE_ID = "youtubeId";
    }

    @Override
    public View onCreateView(final LayoutInflater inflater,
                             final ViewGroup container,
                             final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_content_item, container, false);
    }
}
