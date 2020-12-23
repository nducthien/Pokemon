package pokemon.com.wall.pokemonfun.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import pokemon.com.wall.pokemonfun.R;
import pokemon.com.wall.pokemonfun.adapter.PokemonNextEvolutionAdapter;
import pokemon.com.wall.pokemonfun.adapter.PokemonPrevEvolutionAdapter;
import pokemon.com.wall.pokemonfun.adapter.PokemonTypeAdapter;
import pokemon.com.wall.pokemonfun.common.Common;
import pokemon.com.wall.pokemonfun.model.Pokemon;
import pokemon.com.wall.pokemonfun.model.PrevEvolution;


public class PokemonDetail extends Fragment {

    private ImageView imgPokemon;
    private TextView tvName;
    private TextView tvHeight;
    private TextView tvWeight;
    private TextView tvType;
    private RecyclerView recyclerType;
    private RecyclerView recyclerWeakness;
    private RecyclerView recyclerPrev;
    private RecyclerView recyclerNext;

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
        View view = inflater.inflate(R.layout.fragment_pokemon_detail, container, false);

        Pokemon pokemon;

        // get position from Argument
        if (getArguments().get("num") == null) {
            pokemon = Common.commonPokemonList.get(getArguments().getInt("position"));

        } else {
            pokemon = Common.findPokemonByNum(getArguments().getString("num"));
        }

        initComponent(view);
        setDetailPokemon(pokemon);

        return view;
    }

    private void initComponent(View view) {
        imgPokemon = view.findViewById(R.id.imgPokemon);
        tvName = view.findViewById(R.id.tvName);
        tvHeight = view.findViewById(R.id.tvHeight);
        tvWeight = view.findViewById(R.id.tvWeight);
        tvType = view.findViewById(R.id.tvType);

        recyclerType = view.findViewById(R.id.recyclerType);
        recyclerType.setHasFixedSize(true);
        recyclerType.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        recyclerWeakness = view.findViewById(R.id.recyclerWeakness);
        recyclerWeakness.setHasFixedSize(true);
        recyclerWeakness.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        recyclerPrev = view.findViewById(R.id.recyclerPrev);
        recyclerPrev.setHasFixedSize(true);
        recyclerPrev.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        recyclerNext = view.findViewById(R.id.recyclerNext);
        recyclerNext.setHasFixedSize(true);
        recyclerNext.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
    }

    @SuppressLint("SetTextI18n")
    private void setDetailPokemon(Pokemon pokemon) {
        // Load image
        Glide.with(getActivity()).load(pokemon.getImg()).into(imgPokemon);

        tvName.setText(pokemon.getName());
        tvHeight.setText("Height: " + pokemon.getHeight());
        tvWeight.setText("Weight: " + pokemon.getWeight());

        // Set type
        PokemonTypeAdapter typeAdapter = new PokemonTypeAdapter(getActivity(), pokemon.getType());
        recyclerType.setAdapter(typeAdapter);

        // Set Weakness
        PokemonTypeAdapter weaknessAdapter = new PokemonTypeAdapter(getActivity(), pokemon.getWeaknesses());
        recyclerWeakness.setAdapter(weaknessAdapter);

        // Set Evolution
        PokemonPrevEvolutionAdapter prevEvolutionAdapter = new PokemonPrevEvolutionAdapter(getActivity(), pokemon.getPrev_evolution());
        recyclerPrev.setAdapter(prevEvolutionAdapter);

        // Set Next Evolution
        PokemonNextEvolutionAdapter nextEvolutionAdapter = new PokemonNextEvolutionAdapter(getActivity(), pokemon.getNext_evolution());
        recyclerNext.setAdapter(nextEvolutionAdapter);

    }
}