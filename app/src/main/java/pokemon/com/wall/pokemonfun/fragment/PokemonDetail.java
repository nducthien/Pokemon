package pokemon.com.wall.pokemonfun.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pokemon.com.wall.pokemonfun.R;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 *
 * write LocalBroadCast to request MainActivity replace fragment when we click
 */
public class PokemonDetail extends Fragment {

    static PokemonDetail instance;

    public static PokemonDetail getInstance() {
        if (instance == null)
            instance = new PokemonDetail();
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pokemon_detail, container, false);
    }
}