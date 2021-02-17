package main.java.com.poke.minmax;

import java.util.List;

public class PokeApi {

    String baseURL = "https://pokeapi.co/api/v2/";

    PokeApi() {

    }

    public String GetType(String pokeName) {
        String requestURL = baseURL + pokeName + "/types";
        return "";
    }

    public List<Integer> GetStats(String pokeName) {
        String requestURL = baseURL + pokeName + "/stats";
        return null;
    }

    
    
}
