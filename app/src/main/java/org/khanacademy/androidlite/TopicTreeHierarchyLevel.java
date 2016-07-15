package org.khanacademy.androidlite;

public enum TopicTreeHierarchyLevel {
    DOMAIN("domains"),
    SUBJECT("subjects"),
    TOPIC("topics"),
    TUTORIAL("tutorials");

    public final String path;

    TopicTreeHierarchyLevel(final String path) {
        this.path = path;
    }
}
