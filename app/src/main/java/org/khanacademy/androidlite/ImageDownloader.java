package org.khanacademy.androidlite;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public final class ImageDownloader {
    public static void downloadImage(final ImageView imageView, final URL url) {
        new DownloadImageTask(imageView).execute(url);
    }

    private static class DownloadImageTask extends AsyncTask<URL, Void, Bitmap> {
        private final ImageView mImageView;

        DownloadImageTask(final ImageView imageView) {
            mImageView = imageView;
        }

        protected Bitmap doInBackground(final URL... urls) {
            if (urls.length != 1) {
                throw new RuntimeException("Invalid number of URLs: " + urls.length);
            }

            Bitmap bitmap;
            try {
                final InputStream inputStream = urls[0].openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
                inputStream.close();
            } catch (final IOException e) {
                throw new RuntimeException("Failed to fetch JSON", e);
            }

            return bitmap;
        }

        protected void onPostExecute(final Bitmap bitmap) {
            mImageView.setImageBitmap(bitmap);
        }
    }
}
