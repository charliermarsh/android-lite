package org.khanacademy.androidlite;

public class Topic extends Node {
    @Override
    public int kind() {
        return Kind.TOPIC;
    }

    public Topic(final String slug, final String title, final int domain) {
        super(slug, title, domain);
    }
}
