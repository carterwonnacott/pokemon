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

    PokeCalc() {
        pokemonFetcher = new PokemonFetcher();
        typeFetcher = new TypeFetcher();
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

            JSONArray stats = (JSONArray) pokeJSON.get("stats");
            int attack = (int) stats.getJSONObject(1).get("base_stat");
            int defense = (int) stats.getJSONObject(2).get("base_stat");
            int spAttack = (int) stats.getJSONObject(3).get("base_stat");
            int spDefense = (int) stats.getJSONObject(4).get("base_stat");

            // attack/defense stats are switched here since we are calculating the value of
            // said stats on our pokemon against the fetched opposing pokemon
            HashMap<String, Integer> typeAttackValue = generateTypeStatValueMap(defense);
            HashMap<String, Integer> typeDefenseValue = generateTypeStatValueMap(attack);
            HashMap<String, Integer> typeSpAttackValue = generateTypeStatValueMap(spDefense);
            HashMap<String, Integer> typeSpDefenseValue = generateTypeStatValueMap(spAttack);

            JSONObject damage_relations = (JSONObject) typeJSON.get("damage_relations");
            JSONArray double_damage_from = (JSONArray) damage_relations.get("double_damage_from");
            JSONArray double_damage_to = (JSONArray) damage_relations.get("double_damage_to");
            JSONArray half_damage_from = (JSONArray) damage_relations.get("half_damage_from");
            JSONArray half_damage_to = (JSONArray) damage_relations.get("half_damage_to");
            JSONArray no_damage_from = (JSONArray) damage_relations.get("no_damage_from");
            JSONArray no_damage_to = (JSONArray) damage_relations.get("no_damage_to");

            for (int j = 0; j < double_damage_from.length(); j++) {
                String currType = double_damage_from.getJSONObject(j).getString("name");
                typeAttackValue.put(currType, typeAttackValue.get(currType)*2);
                typeSpAttackValue.put(currType, typeSpAttackValue.get(currType)*2);
            }
            for (int j = 0; j < double_damage_to.length(); j++) {
                String currType = double_damage_to.getJSONObject(j).getString("name");
                typeDefenseValue.put(currType, typeDefenseValue.get(currType)/2);
                typeSpDefenseValue.put(currType, typeSpDefenseValue.get(currType)/2);
            }
            for (int j = 0; j < half_damage_from.length(); j++) {
                String currType = half_damage_from.getJSONObject(j).getString("name");
                typeAttackValue.put(currType, typeAttackValue.get(currType)/2);
                typeSpAttackValue.put(currType, typeSpAttackValue.get(currType)/2);
            }
            for (int j = 0; j < half_damage_to.length(); j++) {
                String currType = half_damage_to.getJSONObject(j).getString("name");
                typeDefenseValue.put(currType, typeDefenseValue.get(currType)*2);
                typeSpDefenseValue.put(currType, typeSpDefenseValue.get(currType)*2);
            }
            for (int j = 0; j < no_damage_from.length(); j++) {
                String currType = no_damage_from.getJSONObject(j).getString("name");
                typeAttackValue.put(currType, typeAttackValue.get(currType)*0);
                typeSpAttackValue.put(currType, typeSpAttackValue.get(currType)*0);
            }
            for (int j = 0; j < no_damage_to.length(); j++) {
                String currType = no_damage_to.getJSONObject(j).getString("name");
                typeDefenseValue.put(currType, typeDefenseValue.get(currType)*8);
                typeSpDefenseValue.put(currType, typeSpDefenseValue.get(currType)*8);
            }

        }
    }

    private HashMap<String, Integer> generateTypeStatValueMap(int base_stat) {
        HashMap<String, Integer> returnMap = new HashMap<>();
        returnMap.put("bug",        base_stat);
        returnMap.put("dark",       base_stat);
        returnMap.put("dragon",     base_stat);
        returnMap.put("electric",   base_stat);
        returnMap.put("fairy",      base_stat);
        returnMap.put("fighting",   base_stat);
        returnMap.put("fire",       base_stat);
        returnMap.put("flying",     base_stat);
        returnMap.put("ghost",      base_stat);
        returnMap.put("grass",      base_stat);
        returnMap.put("ground",     base_stat);
        returnMap.put("ice",        base_stat);
        returnMap.put("normal",     base_stat);
        returnMap.put("poison",     base_stat);
        returnMap.put("psychic",    base_stat);
        returnMap.put("rock",       base_stat);
        returnMap.put("steel",      base_stat);
        returnMap.put("water",      base_stat);
        return returnMap;
    }
}
