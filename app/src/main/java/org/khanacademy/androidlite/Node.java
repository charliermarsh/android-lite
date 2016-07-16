package org.khanacademy.androidlite;

import static org.khanacademy.androidlite.Utils.checkNotNull;

public abstract class Node {
    public final String slug;
    public final String title;
    public final int domain;

    public abstract int kind();

    public Node(final String slug, final String title, final int domain) {
        this.slug = checkNotNull(slug);
        this.title = checkNotNull(title);
        this.domain = domain;
    }
}
