package xyz.kida.rawg.presentation.search.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.kida.rawg.R;
import xyz.kida.rawg.data.di.FakeDependencyInjection;
import xyz.kida.rawg.presentation.model.GameToGameViewModelMapper;
import xyz.kida.rawg.presentation.model.GameViewModel;
import xyz.kida.rawg.presentation.search.SearchContract;
import xyz.kida.rawg.presentation.search.adapter.SearchAdapter;
import xyz.kida.rawg.presentation.search.model.SearchListener;
import xyz.kida.rawg.presentation.search.presenter.SearchPresenter;

public class SearchFragment extends Fragment implements SearchContract.View, SearchListener {

    @BindView(R.id.fragment_search_recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.search_view)
    SearchView searchView;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    private View rootView;
    private SearchAdapter searchAdapter;
    private SearchContract.Presenter presenter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        configureSearchView();
        configureRecyclerView();
        presenter = new SearchPresenter(FakeDependencyInjection.getGameDisplayRepository(), new GameToGameViewModelMapper());
        presenter.attachView(this);
    }

    private void configureSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            private Timer timer = new Timer();

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String s) {
                if (s.length() == 0) {
                    presenter.cancelSubscription();
                    progressBar.setVisibility(View.GONE);
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    timer.cancel();
                    timer = new Timer();
                    int sleep = 350;
                    if (s.length() == 1)
                        sleep = 5000;
                    else if (s.length() <= 3)
                        sleep = 300;
                    else if (s.length() <= 5)
                        sleep = 200;
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            presenter.searchGames(s);
                        }
                    }, sleep);
                }
                return true;
            }
        });
    }

    private void configureRecyclerView() {
        searchAdapter = new SearchAdapter(this);
        recyclerView.setAdapter(searchAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void showGames(List<GameViewModel> gameViewModels) {
        searchAdapter.bindViewModels(gameViewModels);
    }

    @Override
    public void onAddGameToFavorites(String gameId) {
        presenter.addGameToFavorites(gameId);
    }

    @Override
    public void onGameAddToFavorites() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.presenter.detachView();
    }
}
