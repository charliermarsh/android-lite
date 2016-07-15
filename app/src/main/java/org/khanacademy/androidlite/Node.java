package org.khanacademy.androidlite;

public abstract class Node {
    public final String slug;
    public final String title;

    public abstract Kind kind();

    public Node(final String slug, final String title) {
        this.slug = slug;
        this.title = title;
    }
}
