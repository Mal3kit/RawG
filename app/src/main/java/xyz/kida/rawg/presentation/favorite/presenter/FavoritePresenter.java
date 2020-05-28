package xyz.kida.rawg.presentation.favorite.presenter;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.ResourceSubscriber;
import xyz.kida.rawg.data.entity.GameEntity;
import xyz.kida.rawg.data.repository.GameDisplayRepository;
import xyz.kida.rawg.presentation.favorite.FavoriteContract;
import xyz.kida.rawg.presentation.favorite.mapper.GameEntityToGameViewModelMapper;

public class FavoritePresenter implements FavoriteContract.Presenter {

    private CompositeDisposable compositeDisposable;
    private GameDisplayRepository repository;
    private FavoriteContract.View view;
    private GameEntityToGameViewModelMapper mapper;

    public FavoritePresenter(GameDisplayRepository repository, GameEntityToGameViewModelMapper mapper) {
        this.compositeDisposable = new CompositeDisposable();
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public void getFavorites() {
        compositeDisposable.add(repository.loadFavorites()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResourceSubscriber<List<GameEntity>>() {
                    @Override
                    public void onNext(List<GameEntity> gameEntities) {
                        view.displayFavorites(gameEntities
                                .stream()
                                .map(entity -> mapper.toGameViewModel(entity))
                                .collect(Collectors.toList()));
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        System.err.println("Error : " + throwable.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    @Override
    public void deleteGameFromFavorites(String gameID) {
        compositeDisposable.add(repository.removeGameFromFavorites(gameID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        view.onGameRemoveFromFavorites();
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.err.println("Error : " + e.getMessage());
                    }
                }));

    }

    @Override
    public void attachView(FavoriteContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        compositeDisposable.dispose();
        view = null;
    }
}
