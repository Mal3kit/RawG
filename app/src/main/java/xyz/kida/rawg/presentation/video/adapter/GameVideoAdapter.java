package xyz.kida.rawg.presentation.video.adapter;

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
import xyz.kida.rawg.presentation.model.GameVideoViewModel;
import xyz.kida.rawg.presentation.video.model.GameVideoListener;
import xyz.kida.rawg.presentation.video.model.GameVideoViewHolder;

public class GameVideoAdapter extends RecyclerView.Adapter<GameVideoViewHolder> {

    private List<GameVideoViewModel> gameVideoViewModels;
    private final GameVideoListener gameVideoListener;

    public GameVideoAdapter(GameVideoListener gameVideoListener) {
        this.gameVideoViewModels = new ArrayList<>();
        this.gameVideoListener = gameVideoListener;
    }

    public void bindViewModels(List<GameVideoViewModel> gameVideoViewModels) {
        this.gameVideoViewModels.addAll(gameVideoViewModels
                .stream()
                .collect(Collectors.toList()));
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GameVideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_video, parent, false);
        return new GameVideoViewHolder(view, gameVideoListener);
    }

    @Override
    public void onBindViewHolder(@NonNull GameVideoViewHolder holder, int position) {
        holder.updateWithGameVideoView(this.gameVideoViewModels.get(position));
    }

    @Override
    public int getItemCount() {
        return this.gameVideoViewModels.size();
    }
}
