package com.poke.minmax.calc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.poke.minmax.api.PokemonFetcher;
import com.poke.minmax.api.TypeFetcher;

import org.json.JSONArray;
import org.json.JSONObject;

public class PokeCalc {
    PokemonFetcher pokemonFetcher;
    TypeFetcher typeFetcher;
    StatValMap totalValMap;

    PokeCalc() {
        pokemonFetcher = new PokemonFetcher();
        typeFetcher = new TypeFetcher();
        totalValMap = new StatValMap(0, 0, 0, 0);
    }

    public void calculateAll(List<String> pokemon) {
        for (String poke : pokemon) calculate(poke);

    }

    public void calculate(String pokemon) {
        JSONObject pokeJSON = null;
        try {
            pokeJSON = pokemonFetcher.fetchPokemon(pokemon);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (pokeJSON == null) return;
        
        JSONArray stats = (JSONArray) pokeJSON.get("stats");
        int attack = (int) stats.getJSONObject(1).get("base_stat");
        int defense = (int) stats.getJSONObject(2).get("base_stat");
        int spAttack = (int) stats.getJSONObject(3).get("base_stat");
        int spDefense = (int) stats.getJSONObject(4).get("base_stat");

        // attack/defense stats are switched here since we are calculating the value of
        // said stats on our pokemon against the fetched opposing pokemon
        StatValMap statValMap = new StatValMap(defense, attack, spDefense, spAttack);

        JSONArray types = (JSONArray) pokeJSON.get("types");
        for (int i = 0; i < types.length(); i++) {
            JSONObject typeInfo = (JSONObject) (types.getJSONObject(i).get("type"));
            String typeName = (String) typeInfo.get("name");
            JSONObject typeJSON = null;
            try {
                typeJSON = typeFetcher.fetchType(typeName);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (typeJSON == null) return;

            JSONObject damage_relations = (JSONObject) typeJSON.get("damage_relations");
            JSONArray double_damage_from = (JSONArray) damage_relations.get("double_damage_from");
            JSONArray double_damage_to = (JSONArray) damage_relations.get("double_damage_to");
            JSONArray half_damage_from = (JSONArray) damage_relations.get("half_damage_from");
            JSONArray half_damage_to = (JSONArray) damage_relations.get("half_damage_to");
            JSONArray no_damage_from = (JSONArray) damage_relations.get("no_damage_from");
            JSONArray no_damage_to = (JSONArray) damage_relations.get("no_damage_to");

            for (int j = 0; j < double_damage_from.length(); j++) {
                String currType = double_damage_from.getJSONObject(j).getString("name");
                statValMap.modify_typeAttackVals_by_mult(currType, 2);
            }
            for (int j = 0; j < double_damage_to.length(); j++) {
                String currType = double_damage_to.getJSONObject(j).getString("name");
                statValMap.modify_typeDefenseVals_by_div(currType, 2);
            }
            for (int j = 0; j < half_damage_from.length(); j++) {
                String currType = half_damage_from.getJSONObject(j).getString("name");
                statValMap.modify_typeAttackVals_by_div(currType, 2);
            }
            for (int j = 0; j < half_damage_to.length(); j++) {
                String currType = half_damage_to.getJSONObject(j).getString("name");
                statValMap.modify_typeDefenseVals_by_mult(currType, 2);
            }
            for (int j = 0; j < no_damage_from.length(); j++) {
                String currType = no_damage_from.getJSONObject(j).getString("name");
                statValMap.modify_typeAttackVals_by_mult(currType, 0);
            }
            for (int j = 0; j < no_damage_to.length(); j++) {
                String currType = no_damage_to.getJSONObject(j).getString("name");
                statValMap.modify_typeDefenseVals_by_mult(currType, 8); // is this how we want to value no_damage_to?
            }

        }

        totalValMap.add(statValMap);
        
    }
}
