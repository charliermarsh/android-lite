package org.khanacademy.androidlite;

import android.app.Activity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public final class VideoActivity extends Activity {
    static final class Keys {
        static final String TITLE = "title";
        static final String YOUTUBE_ID = "youtubeId";
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        // Load the appropriate URL in the VideoView.
        final String youtubeId = getIntent().getStringExtra(Keys.YOUTUBE_ID);
        final String videoUrl = UrlBuilder.forYoutubeId(youtubeId);
        final VideoView videoView = (VideoView) findViewById(R.id.video_view);
        videoView.setVideoPath(videoUrl);
        videoView.start();

        // Add a MediaController, which gives us play/pause, etc.
        final MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
    }
}
