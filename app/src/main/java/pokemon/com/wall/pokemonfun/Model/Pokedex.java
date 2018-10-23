package pokemon.com.wall.pokemonfun.Model;

import java.util.List;

public class Pokedex {

    private List<Pokemon> pokemon;

    public Pokedex() {
    }

    public Pokedex(List<Pokemon> pokemon) {
        this.pokemon = pokemon;
    }

    public List<Pokemon> getPokemons() {
        return pokemon;
    }

    public void setPokemons(List<Pokemon> pokemon) {
        this.pokemon = pokemon;
    }
}
