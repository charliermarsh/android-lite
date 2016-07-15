package org.khanacademy.androidlite;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoFragment extends Fragment {
    static final class Keys {
        static final String TITLE = "title";
        static final String YOUTUBE_ID = "youtubeId";
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater,
                             final ViewGroup container,
                             final Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_video, container, false
        );

        // Load the appropriate URL in the VideoView.
        final String youtubeId = getArguments().getString(Keys.YOUTUBE_ID);
        final String videoUrl = UrlBuilder.forYoutubeId(youtubeId);
        final VideoView videoView = (VideoView) rootView.findViewById(R.id.video_view);
        videoView.setVideoPath(videoUrl);
        videoView.start();

        // Add a MediaController, which gives us play/pause, etc.
        final MediaController mediaController = new MediaController(getActivity());
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        return rootView;
    }
}
