package com.poke.minmax.calc;

import java.util.HashMap;
import java.util.Map;

public class StatValMap {
    HashMap<String, Integer> typeAttackValue;
    HashMap<String, Integer> typeDefenseValue;
    HashMap<String, Integer> typeSpAttackValue;
    HashMap<String, Integer> typeSpDefenseValue;

    StatValMap(int attack, int defense, int spAttack, int spDefense) {
        typeAttackValue = generateTypeStatValueMap(attack);
        typeDefenseValue = generateTypeStatValueMap(defense);
        typeSpAttackValue = generateTypeStatValueMap(spAttack);
        typeSpDefenseValue = generateTypeStatValueMap(spDefense);
    }

    public void add(StatValMap other) {
        for (Map.Entry<String, Integer> entry : other.typeAttackValue.entrySet()) {
            this.typeAttackValue.put(entry.getKey(), 
                this.typeAttackValue.get(entry.getKey()) + other.typeAttackValue.get(entry.getKey()));
        }
        for (Map.Entry<String, Integer> entry : other.typeDefenseValue.entrySet()) {
            this.typeDefenseValue.put(entry.getKey(), 
                this.typeDefenseValue.get(entry.getKey()) + other.typeDefenseValue.get(entry.getKey()));
        }
        for (Map.Entry<String, Integer> entry : other.typeSpAttackValue.entrySet()) {
            this.typeSpAttackValue.put(entry.getKey(), 
                this.typeSpAttackValue.get(entry.getKey()) + other.typeSpAttackValue.get(entry.getKey()));
        }
        for (Map.Entry<String, Integer> entry : other.typeSpDefenseValue.entrySet()) {
            this.typeSpDefenseValue.put(entry.getKey(), 
                this.typeSpDefenseValue.get(entry.getKey()) + other.typeSpDefenseValue.get(entry.getKey()));
        }
    }

    public void modify_typeAttackVals_by_mult(String type, int factor) {
        typeAttackValue.put(type, typeAttackValue.get(type) * factor);
        typeSpAttackValue.put(type, typeSpAttackValue.get(type) * factor);
    }

    public void modify_typeAttackVals_by_div(String type, int divisor) {
        typeAttackValue.put(type, typeAttackValue.get(type) / divisor);
        typeSpAttackValue.put(type, typeSpAttackValue.get(type) / divisor);
    }

    public void modify_typeDefenseVals_by_mult(String type, int factor) {
        typeDefenseValue.put(type, typeDefenseValue.get(type) * factor);
        typeSpDefenseValue.put(type, typeSpDefenseValue.get(type) * factor);
    }

    public void modify_typeDefenseVals_by_div(String type, int divisor) {
        typeDefenseValue.put(type, typeDefenseValue.get(type) / divisor);
        typeSpDefenseValue.put(type, typeSpDefenseValue.get(type) / divisor);
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
