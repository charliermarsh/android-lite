package org.khanacademy.androidlite;

import static org.khanacademy.androidlite.Utils.checkNotNull;

public abstract class Node {
    public final String slug;
    public final String title;
    public final Domain domain;

    public abstract Kind kind();

    public Node(final String slug, final String title, final Domain domain) {
        this.slug = checkNotNull(slug);
        this.title = checkNotNull(title);
        System.out.println("Slug etc " + slug + " " + title);
        this.domain = checkNotNull(domain);
    }
}
