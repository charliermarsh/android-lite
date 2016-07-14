package org.khanacademy.androidlite;

import org.json.JSONException;

public interface JsonDecoder<U, V> {
    V fromJson(U json) throws JSONException;
}
