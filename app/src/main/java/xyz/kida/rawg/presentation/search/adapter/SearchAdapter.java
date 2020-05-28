package xyz.kida.rawg.presentation.search.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import xyz.kida.rawg.R;
import xyz.kida.rawg.presentation.model.GameViewModel;
import xyz.kida.rawg.presentation.search.model.SearchListener;
import xyz.kida.rawg.presentation.search.model.SearchViewHolder;

public class SearchAdapter extends RecyclerView.Adapter<SearchViewHolder> {

    private List<GameViewModel> gameViewModels;
    private final SearchListener searchListener;

    public SearchAdapter(SearchListener searchListener) {
        this.gameViewModels = new ArrayList<>();
        this.searchListener = searchListener;
    }

    public void bindViewModels(List<GameViewModel> gameViewModels) {
        this.gameViewModels.clear();
        this.gameViewModels.addAll(gameViewModels
                .stream()
                .collect(Collectors.toList()));
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_game, parent, false);
        return new SearchViewHolder(view, searchListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        holder.updateWithGameView(this.gameViewModels.get(position));
    }

    @Override
    public int getItemCount() {
        return this.gameViewModels.size();
    }
}
