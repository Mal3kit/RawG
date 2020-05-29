package xyz.kida.rawg.presentation.video.model;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.kida.rawg.R;
import xyz.kida.rawg.presentation.model.GameVideoViewModel;

public class GameVideoViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.video_thumbnail_imageview)
    ImageView gameVideoImageView;

    @BindView(R.id.video_title_textview)
    TextView gameVideoTitleTextView;

    @BindView(R.id.video_channel_textview)
    TextView gameVideoChannelNameTextView;

    @BindView(R.id.video_count_textview)
    TextView gameVideoViewsTextView;

    private GameVideoViewModel gameVideoViewModel;
    private GameVideoListener gameVideoListener;
    private View view;

    public GameVideoViewHolder(@NonNull View view, GameVideoListener gameVideoListener) {
        super(view);
        this.view = view;
        ButterKnife.bind(this, view);
        this.gameVideoListener = gameVideoListener;
        setupListeners();
    }

    private void setupListeners() {
        gameVideoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameVideoListener.onOpenVideo(gameVideoViewModel.getId());
            }
        });
    }

    public void updateWithGameVideoView(GameVideoViewModel gameVideoViewModel) {
        this.gameVideoViewModel = gameVideoViewModel;
        this.gameVideoTitleTextView.setText(gameVideoViewModel.getName());
        this.gameVideoChannelNameTextView.setText(gameVideoViewModel.getChannelName());
        this.gameVideoViewsTextView.setText(gameVideoViewModel.getViewCount().toString());
        Glide.with(view)
                .load(gameVideoViewModel.getImageUrl())
                .centerCrop()
                .into(gameVideoImageView);
    }
}
