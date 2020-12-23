package pokemon.com.wall.pokemonfun.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import pokemon.com.wall.pokemonfun.Interface.ItemClickListener;
import pokemon.com.wall.pokemonfun.common.Common;
import pokemon.com.wall.pokemonfun.model.Pokemon;
import pokemon.com.wall.pokemonfun.R;

public class PokemonListAdapter extends RecyclerView.Adapter<PokemonListAdapter.MyViewHolder> {

    Context context;
    List<Pokemon> pokemonList;

    public PokemonListAdapter(Context context, List<Pokemon> pokemonList) {
        this.context = context;
        this.pokemonList = pokemonList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.pokemon_list_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // Load img
        Glide.with(context).load(pokemonList.get(position).getImg()).into(holder.img_pokemon);
        // Set name
        holder.tv_pokemon_name.setText(pokemonList.get(position).getName());

        // Event
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                /**
                 * Toast.makeText(context, "Click at Pokemon: " + pokemonList.get(position).getName(), Toast.LENGTH_SHORT).show();
                 * write LocalBroadCast to request MainActivity replace fragment when we click
                 */

                LocalBroadcastManager.getInstance(context)
                        .sendBroadcast(new Intent(Common.KEY_ENABLE_HOME)
                                .putExtra("num", pokemonList.get(position).getNum()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return pokemonList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView img_pokemon;
        TextView tv_pokemon_name;

        ItemClickListener itemClickListener;

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        public MyViewHolder(View itemView) {
            super(itemView);

            img_pokemon = itemView.findViewById(R.id.img_pokemon);
            tv_pokemon_name = itemView.findViewById(R.id.tv_pokemon_name);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(itemView, getAdapterPosition());
        }
    }
}
