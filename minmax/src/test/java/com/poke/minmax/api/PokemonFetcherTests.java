package com.poke.minmax.api;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PokemonFetcherTests {
    PokemonFetcher pokemonFetcher;

    @BeforeEach
    public void setup() {
        pokemonFetcher = new PokemonFetcher();
    }

    @Test
    @DisplayName("Should return valid data with valid pokemon name")
    public void should_returnValidData_with_validPokemonName() {
        JSONObject ditto = null;
        try {
            ditto = pokemonFetcher.fetchPokemon("butterfree");
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertNotNull(ditto);
    }
}
