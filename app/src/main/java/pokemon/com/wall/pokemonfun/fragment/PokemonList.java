package pokemon.com.wall.pokemonfun.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import pokemon.com.wall.pokemonfun.R;
import pokemon.com.wall.pokemonfun.adapter.PokemonListAdapter;
import pokemon.com.wall.pokemonfun.common.Common;
import pokemon.com.wall.pokemonfun.common.ItemOffsetDecoration;
import pokemon.com.wall.pokemonfun.model.Pokedex;
import pokemon.com.wall.pokemonfun.model.Pokemon;
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

    PokemonListAdapter adapter, search_adapter;
    List<String> last_suggest = new ArrayList<>();

    MaterialSearchBar materialSearchBar;

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

        // Setup SearchBar
        materialSearchBar = view.findViewById(R.id.search_bar);
        materialSearchBar.setHint("Enter Pokemon Name");
        materialSearchBar.setCardViewElevation(10);
        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<String> suggest = new ArrayList<>();
                for (String search : last_suggest) {
                    if (search.toLowerCase().contains(materialSearchBar.getText().toLowerCase())) {
                        suggest.add(search);
                    }
                }
                materialSearchBar.setLastSuggestions(suggest);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                if (!enabled) {
                    rcv_pokemon_list.setAdapter(adapter); // return default adapter
                }
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                startSearch(text);
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });

        fetchData();
        return view;
    }

    private void startSearch(CharSequence text) {
        if (Common.commonPokemonList.size() > 0) {
            List<Pokemon> result = new ArrayList<>();
            for (Pokemon pokemon : Common.commonPokemonList) {
                if (pokemon.getName().toLowerCase().contains(text.toString().toLowerCase())) {
                    result.add(pokemon);
                }
            }
            search_adapter = new PokemonListAdapter(getActivity(), result);
            rcv_pokemon_list.setAdapter(search_adapter);
        }
    }

    private void fetchData() {

        compositeDisposable.add(iPokemonDex.getListPokemon()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Pokedex>() {
                    @Override
                    public void accept(Pokedex pokedex) throws Exception {
                        Common.commonPokemonList = pokedex.getPokemons();
                        adapter = new PokemonListAdapter(getActivity(), Common.commonPokemonList);

                        rcv_pokemon_list.setAdapter(adapter);
                        last_suggest.clear();
                        for (Pokemon pokemon : Common.commonPokemonList) {
                            last_suggest.add(pokemon.getName());
                        }
                        materialSearchBar.setVisibility(View.VISIBLE); // display search bar after load all pokemon from db
                        materialSearchBar.setLastSuggestions(last_suggest);
                    }
                })
        );
    }

}
