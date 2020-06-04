package xyz.kida.rawg.presentation.search.presenter;

import java.util.stream.Collectors;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import xyz.kida.rawg.data.api.models.GameSearchResponse;
import xyz.kida.rawg.data.repository.GameDisplayRepository;
import xyz.kida.rawg.presentation.model.GameToGameViewModelMapper;
import xyz.kida.rawg.presentation.search.SearchContract;

public class SearchPresenter implements SearchContract.Presenter {

    private CompositeDisposable compositeDisposable;
    private GameDisplayRepository repository;
    private SearchContract.View view;
    private GameToGameViewModelMapper modelMapper;

    public SearchPresenter(GameDisplayRepository repository, GameToGameViewModelMapper modelMapper) {
        this.compositeDisposable = new CompositeDisposable();
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void searchGames(String keywords) {
        compositeDisposable.clear();
        compositeDisposable.add(repository.getGames(keywords)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<GameSearchResponse>() {
                    @Override
                    public void onSuccess(GameSearchResponse gameSearchResponse) {
                        view.showGames(gameSearchResponse.getGames()
                                .stream()
                                .map(game -> modelMapper.toGameViewModel(game))
                                .collect(Collectors.toList()));
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.err.println("Error : " +e.getMessage());
                    }
                }));
    }

    @Override
    public void addGameToFavorites(String gameID) {
        compositeDisposable.add(repository.addGameToFavorites(gameID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        view.onGameAddToFavorites();
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.err.println("Error :" + e.getMessage());
                    }
                }));
    }

    @Override
    public void attachView(SearchContract.View view) {
        this.view = view;
    }

    @Override
    public void cancelSubscription() {
        compositeDisposable.clear();
    }

    @Override
    public void detachView() {
        compositeDisposable.dispose();
        view = null;
    }


}
