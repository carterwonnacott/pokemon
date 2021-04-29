package com.poke.minmax.calc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.poke.minmax.api.PokemonFetcher;

import org.json.JSONArray;
import org.json.JSONObject;

public class PokeCalc {
    PokemonFetcher pokemonFetcher;

    PokeCalc() {
        pokemonFetcher = new PokemonFetcher();
    }

    HashMap<Integer, Integer> calculate(String pokemon) {
        JSONObject pokeJSON = null;
        try {
            pokeJSON = pokemonFetcher.fetchPokemon(pokemon);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONArray types = (JSONArray) pokeJSON.get("types");
        List<String> typeStrings = new ArrayList<>();
        for (Object type : types) {
            JSONObject typeInfo = (JSONObject) ((JSONObject) type).get("type");
            String typeName = (String) typeInfo.get("name");
            typeStrings.add(typeName);
        }

        //TODO: get type strengths/weaknesses and 
        return null;
    }
}
