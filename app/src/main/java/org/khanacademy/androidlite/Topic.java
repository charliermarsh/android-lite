package org.khanacademy.androidlite;

public class Topic extends Node {
    @Override
    public Kind kind() {
        return Kind.TOPIC;
    }

    public Topic(final String slug, final String title, final Domain domain) {
        super(slug, title, domain);
    }
}
