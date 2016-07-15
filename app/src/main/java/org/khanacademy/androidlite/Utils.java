package org.khanacademy.androidlite;

public final class Utils {
    private Utils() {}

    public static <T> T checkNotNull(final T reference) {
        if (reference == null) {
            throw new NullPointerException();
        }
        return reference;
    }
}
