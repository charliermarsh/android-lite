package org.khanacademy.androidlite;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public final class JsonFetcher {
    private static final int TIMEOUT_MS = 10000;

    public static JSONObject fetchJson(final URL url) throws IOException, JSONException {
        final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(TIMEOUT_MS);
        connection.setReadTimeout(TIMEOUT_MS);

        try {
            connection.connect();

            switch (connection.getResponseCode()) {
                case 200:
                case 201:
                    final BufferedReader reader = new BufferedReader(new InputStreamReader(
                            connection.getInputStream()
                    ));

                    final StringBuilder builder = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        builder.append(line).append("\n");
                    }
                    reader.close();

                    return new JSONObject(builder.toString());
            }

            throw new IOException("Bad response: " + connection.getResponseCode());
        } finally {
            connection.disconnect();
        }
    }

    public static void fetchJsonAsync(final URL url, final Action1<JSONObject> onFetch) {
        final JsonFetchTask asyncTask = new JsonFetchTask(onFetch);
        asyncTask.execute(url);
    }

    private static class JsonFetchTask extends AsyncTask<URL, Boolean, JSONObject> {
        private final Action1<JSONObject> mOnFetch;

        JsonFetchTask(final Action1<JSONObject> onFetch) {
            mOnFetch = onFetch;
        }

        @Override
        protected JSONObject doInBackground(final URL... urls) {
            if (urls.length != 1) {
                throw new RuntimeException("Invalid number of URLs: " + urls.length);
            }

            JSONObject jsonObject;
            try {
                jsonObject = JsonFetcher.fetchJson(urls[0]);
            } catch (final IOException | JSONException e) {
                throw new RuntimeException("Failed to fetch JSON", e);
            }

            return jsonObject;
        }

        @Override
        protected void onProgressUpdate(final Boolean... progress) {}

        @Override
        protected void onPostExecute(final JSONObject jsonObject) {
            mOnFetch.call(jsonObject);
        }
    }
}
