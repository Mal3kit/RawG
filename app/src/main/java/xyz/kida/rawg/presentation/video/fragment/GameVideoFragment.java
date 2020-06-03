package xyz.kida.rawg.presentation.video.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.kida.rawg.R;
import xyz.kida.rawg.data.di.FakeDependencyInjection;
import xyz.kida.rawg.presentation.model.GameVideoToGameVideoViewModelMapper;
import xyz.kida.rawg.presentation.model.GameVideoViewModel;
import xyz.kida.rawg.presentation.video.GameVideoContract;
import xyz.kida.rawg.presentation.video.adapter.GameVideoAdapter;
import xyz.kida.rawg.presentation.video.model.GameVideoListener;
import xyz.kida.rawg.presentation.video.presenter.GameVideoPresenter;

public class GameVideoFragment extends Fragment implements GameVideoContract.View, GameVideoListener {

    @BindView(R.id.video_fragment_recycler_view)
    RecyclerView recyclerView;

    private View rootView;
    private GameVideoAdapter gameVideoAdapter;
    private GameVideoContract.Presenter presenter;

    private static final String YOUTUBE_URL = "https://www.youtube.com/watch?v=";

    public GameVideoFragment() {

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(R.layout.fragment_video, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        configureRecyclerView();
        presenter = new GameVideoPresenter(FakeDependencyInjection.getGameDisplayRepository(), new GameVideoToGameVideoViewModelMapper());
        presenter.attachView(this);
        presenter.getVideosForFavorites();
    }

    private void configureRecyclerView() {
        gameVideoAdapter = new GameVideoAdapter(this);
        recyclerView.setAdapter(gameVideoAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void displayVideosForFavoritesGames(List<GameVideoViewModel> gameVideoViewModels) {
        gameVideoAdapter.bindViewModels(gameVideoViewModels);
    }

    @Override
    public void onOpenVideo(String videoUrl) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(YOUTUBE_URL + videoUrl));
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}
