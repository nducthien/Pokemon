package pokemon.com.wall.pokemonfun.retrofit;

import io.reactivex.Observable;
import pokemon.com.wall.pokemonfun.model.Pokedex;
import retrofit2.http.GET;

public interface IPokemonDex {
    @GET("pokedex.json")
    Observable<Pokedex> getListPokemon();
}
