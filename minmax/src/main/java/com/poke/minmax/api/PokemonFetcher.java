package com.poke.minmax.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.json.JSONObject;

public class PokemonFetcher {
    String baseURL = "https://pokeapi.co/api/v2/pokemon/";

    public PokemonFetcher() {}

    public JSONObject fetchPokemon(String pokemon) throws IOException {
        String url = baseURL + pokemon;
        Fetcher fetcher = new Fetcher();
        InputStream is= null;
        try {
            is = fetcher.fetch(url);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to fetch pokemon " + pokemon);
        }
        if (is == null) return null;
        BufferedReader streamReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        StringBuilder responseStrBuilder = new StringBuilder();
        String iString;
        while ((iString = streamReader.readLine()) != null) responseStrBuilder.append(iString);
        return new JSONObject(responseStrBuilder.toString());
    }
}
