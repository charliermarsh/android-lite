package org.khanacademy.androidlite;

public class ContentItem extends Node {
    public final String youtubeId;

    public ContentItem(final String slug, final String title, final String youtubeId) {
        super(slug, title);

        this.youtubeId = youtubeId;
    }

    @Override
    public Kind kind() {
        return Kind.CONTENT_ITEM;
    }
}
