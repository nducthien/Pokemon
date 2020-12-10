package pokemon.com.wall.pokemonfun.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import pokemon.com.wall.pokemonfun.R;
import pokemon.com.wall.pokemonfun.adapter.PokemonListAdapter;
import pokemon.com.wall.pokemonfun.common.Common;
import pokemon.com.wall.pokemonfun.common.ItemOffsetDecoration;
import pokemon.com.wall.pokemonfun.model.Pokedex;
import pokemon.com.wall.pokemonfun.retrofit.IPokemonDex;
import pokemon.com.wall.pokemonfun.retrofit.RetrofitClient;
import retrofit2.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 */
public class PokemonList extends Fragment {

    IPokemonDex iPokemonDex;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    RecyclerView rcv_pokemon_list;

    static PokemonList instance;

    public static PokemonList getInstance() {
        if (instance == null)
            instance = new PokemonList();
        return instance;
    }

    public PokemonList() {
        // Required empty public constructor
        Retrofit retrofit = RetrofitClient.getInstance();
        iPokemonDex = retrofit.create(IPokemonDex.class);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pokemon_list, container, false);

        rcv_pokemon_list = view.findViewById(R.id.rcv_list_pokemon);
        rcv_pokemon_list.setHasFixedSize(true);
        rcv_pokemon_list.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        ItemOffsetDecoration itemOffsetDecoration = new ItemOffsetDecoration(getActivity(), R.dimen.spacing);
        rcv_pokemon_list.addItemDecoration(itemOffsetDecoration);

        fetchData();
        return view;
    }

    private void fetchData() {

        compositeDisposable.add(iPokemonDex.getListPokemon()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Pokedex>() {
                    @Override
                    public void accept(Pokedex pokedex) throws Exception {
                        Common.commonpokemonList = pokedex.getPokemons();
                        PokemonListAdapter adapter = new PokemonListAdapter(getActivity(), Common.commonpokemonList);

                        rcv_pokemon_list.setAdapter(adapter);

                    }
                })
        );
    }

}
