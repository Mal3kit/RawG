package xyz.kida.rawg.presentation.search.model;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.kida.rawg.R;
import xyz.kida.rawg.presentation.model.GameViewModel;

public class SearchViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.game_imageview)
    ImageView gameImageView;

    @BindView(R.id.game_name_textview)
    TextView gameNameView;

    @BindView(R.id.game_rating_textview)
    TextView gameRatingView;


    @BindView(R.id.add_button)
    Button addButton;

    private GameViewModel gameViewModel;
    private SearchListener searchListener;
    private View view;

    public SearchViewHolder(View view, SearchListener searchListener) {
        super(view);
        this.view = view;
        ButterKnife.bind(this, view);
        this.searchListener = searchListener;
        setupListeners();
    }

    private void setupListeners() {
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO : set fav ?
                searchListener.onAddGameToFavorites(gameViewModel.getId());
            }
        });
    }

    public void updateWithGameView(GameViewModel gameViewModel) {
        this.gameViewModel = gameViewModel;
        this.gameNameView.setText(gameViewModel.getName());
        this.gameRatingView.setText(gameViewModel.getRating());
        addButton.setVisibility(gameViewModel.isFavorite() ? View.INVISIBLE : View.VISIBLE);
        Glide.with(view)
                .load(gameViewModel.getImageUrl())
                .circleCrop()
                .centerCrop()
                .into(gameImageView);
    }
}