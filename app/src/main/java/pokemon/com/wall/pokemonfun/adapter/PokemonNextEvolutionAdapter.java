package pokemon.com.wall.pokemonfun.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.robertlevonyan.views.chip.Chip;
import com.robertlevonyan.views.chip.OnChipClickListener;

import java.util.ArrayList;
import java.util.List;

import pokemon.com.wall.pokemonfun.Interface.ItemClickListener;
import pokemon.com.wall.pokemonfun.R;
import pokemon.com.wall.pokemonfun.common.Common;
import pokemon.com.wall.pokemonfun.model.NextEvolution;

public class PokemonNextEvolutionAdapter extends RecyclerView.Adapter<PokemonNextEvolutionAdapter.ViewHolder> {
    private Context context;
    private List<NextEvolution> nextEvolutionList;

    public PokemonNextEvolutionAdapter(Context context, List<NextEvolution> nextEvolutionList) {
        this.context = context;
        if (nextEvolutionList != null)
            this.nextEvolutionList = nextEvolutionList;
        else
            this.nextEvolutionList = new ArrayList<>(); // fix crash if pokemon doesn't have prev or next evolution
    }

    @NonNull
    @Override
    public PokemonNextEvolutionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chip_item, parent, false);

        return new PokemonNextEvolutionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonNextEvolutionAdapter.ViewHolder holder, int position) {
        holder.chip.setChipText(nextEvolutionList.get(position).getName());
        holder.chip.changeBackgroundColor(
                Common.getColorByType(
                        Common.findPokemonByNum(
                                nextEvolutionList.get(position).getNum()).getType().get(0))
        );

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(context, "CLick to next evolution Pokemon", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return nextEvolutionList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private Chip chip;
        private ItemClickListener itemClickListener;

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        public ViewHolder(View itemView) {
            super(itemView);

            chip = itemView.findViewById(R.id.chip);
            chip.setOnChipClickListener(new OnChipClickListener() {
                @Override
                public void onChipClick(View v) {
                    itemClickListener.onClick(v, getAdapterPosition());
                }
            });
        }
    }
}
