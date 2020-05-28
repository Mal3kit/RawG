package xyz.kida.rawg.presentation.favorite.model;

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

public class FavoriteViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.game_name_favorite_textview)
    TextView gameNameView;

    @BindView(R.id.game_rating_favorite_textview)
    TextView gameRatingView;

    @BindView(R.id.game_favorite_imageview)
    ImageView gameImageView;

    @BindView(R.id.delete_button)
    Button deleteButton;

    private View view;
    private GameViewModel gameViewModel;
    private FavoriteListener favoriteListener;

    public FavoriteViewHolder(View view, FavoriteListener favoriteListener) {
        super(view);
        this.view = view;
        ButterKnife.bind(this, view);
        setupListeners();
        this.favoriteListener = favoriteListener;
    }

    private void setupListeners() {
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favoriteListener.onDeleteGameFromFavorites(gameViewModel.getId());
            }
        });
    }

    public void updateWithGameView(GameViewModel gameViewModel) {
        this.gameViewModel = gameViewModel;
        this.gameNameView.setText(gameViewModel.getName());
        this.gameRatingView.setText(gameViewModel.getRating());
        Glide.with(view)
                .load(gameViewModel.getImageUrl())
                .centerCrop()
                .into(gameImageView);
    }
}
