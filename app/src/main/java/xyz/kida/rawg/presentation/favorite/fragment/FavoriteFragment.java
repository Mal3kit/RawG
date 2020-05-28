package xyz.kida.rawg.presentation.favorite.fragment;

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
import xyz.kida.rawg.presentation.favorite.FavoriteContract;
import xyz.kida.rawg.presentation.favorite.adapter.FavoriteGameAdapter;
import xyz.kida.rawg.presentation.favorite.mapper.GameEntityToGameViewModelMapper;
import xyz.kida.rawg.presentation.favorite.model.FavoriteListener;
import xyz.kida.rawg.presentation.favorite.presenter.FavoritePresenter;
import xyz.kida.rawg.presentation.model.GameViewModel;

public class FavoriteFragment extends Fragment implements FavoriteContract.View, FavoriteListener {

    @BindView(R.id.favorite_fragment_recycler_view)
    RecyclerView recyclerView;

    private View rootView;
    private FavoriteGameAdapter favoriteGameAdapter;
    private FavoriteContract.Presenter presenter;

    public FavoriteFragment() {

    }

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(R.layout.fragment_favorite, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        configureRecyclerView();
        presenter = new FavoritePresenter(FakeDependencyInjection.getGameDisplayRepository(), new GameEntityToGameViewModelMapper());
        presenter.attachView(this);
        presenter.getFavorites();
    }

    private void configureRecyclerView() {
        favoriteGameAdapter = new FavoriteGameAdapter(this);
        recyclerView.setAdapter(favoriteGameAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void displayFavorites(List<GameViewModel> gameViewModels) {
        favoriteGameAdapter.bindViewModels(gameViewModels);
    }

    @Override
    public void onGameRemoveFromFavorites() {

    }

    @Override
    public void onDeleteGameFromFavorites(String gameID) {
        presenter.deleteGameFromFavorites(gameID);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}
