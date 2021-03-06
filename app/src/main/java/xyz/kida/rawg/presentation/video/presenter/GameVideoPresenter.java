package xyz.kida.rawg.presentation.video.presenter;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import xyz.kida.rawg.data.api.models.GameVideoSearchResponse;
import xyz.kida.rawg.data.repository.GameDisplayRepository;
import xyz.kida.rawg.presentation.model.GameVideoToGameVideoViewModelMapper;
import xyz.kida.rawg.presentation.video.GameVideoContract;

public class GameVideoPresenter implements GameVideoContract.Presenter {

    private CompositeDisposable compositeDisposable;
    private GameDisplayRepository repository;
    private GameVideoContract.View view;
    private GameVideoToGameVideoViewModelMapper modelMapper;

    public GameVideoPresenter(GameDisplayRepository repository, GameVideoToGameVideoViewModelMapper modelMapper) {
        this.compositeDisposable = new CompositeDisposable();
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void getVideosForFavorites() {
        compositeDisposable.add(repository.getVideosForFavoriteGames()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<GameVideoSearchResponse>>() {
                    @Override
                    public void onSuccess(List<GameVideoSearchResponse> gameVideoSearchResponses) {
                        view.displayVideosForFavoritesGames(gameVideoSearchResponses
                                .stream()
                                .flatMap(gameVideoSearchResponse -> gameVideoSearchResponse.getGameVideos()
                                        .stream()
                                        .map(gameVideo -> modelMapper.toGameVideoViewModel(gameVideo)))
                                .collect(Collectors.toList()));
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.err.println("Error : " +e.getMessage());
                    }
                }));
    }

    @Override
    public void attachView(GameVideoContract.View view) {
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
