package org.khanacademy.androidlite;

import android.content.Context;
import android.content.Intent;

public final class IntentCreator {
    private IntentCreator() {}

    public static Intent forAllSubjects(final Context context) {
        return new Intent(context, NodeListActivity.class);
    }

    public static Intent forTopic(final Topic topic, final Context context) {
        final Intent intent = new Intent(context, NodeListActivity.class);
        intent.putExtra(NodeListActivity.Keys.PARENT_SLUG, topic.slug);
        intent.putExtra(NodeListActivity.Keys.PARENT_DOMAIN_SLUG, topic.domain.slug);
        intent.putExtra(NodeListActivity.Keys.TITLE, topic.title);
        return intent;
    }

    public static Intent forContentItem(final ContentItem contentItem, final Context context) {
        final Intent intent = new Intent(context, VideoActivity.class);
        intent.putExtra(VideoActivity.Keys.TITLE, contentItem.title);
        intent.putExtra(VideoActivity.Keys.YOUTUBE_ID, contentItem.youtubeId);
        return intent;
    }
}
