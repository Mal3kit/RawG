package xyz.kida.rawg.presentation.favorite.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import xyz.kida.rawg.R;
import xyz.kida.rawg.presentation.favorite.model.FavoriteListener;
import xyz.kida.rawg.presentation.favorite.model.FavoriteViewHolder;
import xyz.kida.rawg.presentation.model.GameViewModel;

public class FavoriteGameAdapter extends RecyclerView.Adapter<FavoriteViewHolder> {

    private List<GameViewModel> gameViewModels;
    private FavoriteListener listener;

    public FavoriteGameAdapter(FavoriteListener listener) {
        this.gameViewModels = new ArrayList<>();
        this.listener = listener;
    }

    public void bindViewModels(List<GameViewModel> gameViewModels) {
        this.gameViewModels.clear();
        this.gameViewModels.addAll(gameViewModels);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_game_favorite, parent, false);
        return new FavoriteViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        holder.updateWithGameView(gameViewModels.get(position));
    }

    @Override
    public int getItemCount() {
        return gameViewModels.size();
    }
}
