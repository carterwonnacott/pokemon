package com.poke.minmax.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.json.JSONObject;

public class TypeFetcher {
    String baseURL = "https://pokeapi.co/api/v2/type/";

    public TypeFetcher() {}

    public JSONObject fetchType(String type) throws IOException {
        String url = baseURL + type;
        Fetcher fetcher = new Fetcher();
        InputStream is= null;
        try {
            is = fetcher.fetch(url);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to fetch type " + type);
        }
        if (is == null) return null;
        BufferedReader streamReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        StringBuilder responseStrBuilder = new StringBuilder();
        String iString;
        while ((iString = streamReader.readLine()) != null) responseStrBuilder.append(iString);
        return new JSONObject(responseStrBuilder.toString());

}
