package pokemon.com.wall.pokemonfun.Retrofit;

import io.reactivex.Observable;
import pokemon.com.wall.pokemonfun.Model.Pokedex;
import retrofit2.http.GET;

public interface IPokemonDex {
    @GET("pokedex.json")
    Observable<Pokedex> getListPokemon();
}
