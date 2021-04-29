package com.poke.minmax.api;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class Fetcher {
    Fetcher() {}

    InputStream fetch(String url) throws IOException {
        URLConnection connection = new URL(url).openConnection();
        return connection.getInputStream();
    }

}
