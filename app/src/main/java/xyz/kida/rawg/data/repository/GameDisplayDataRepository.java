package xyz.kida.rawg.data.repository;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import xyz.kida.rawg.data.api.models.Game;
import xyz.kida.rawg.data.api.models.GameSearchResponse;
import xyz.kida.rawg.data.api.models.GameVideoSearchResponse;
import xyz.kida.rawg.data.entity.GameEntity;
import xyz.kida.rawg.data.repository.local.GameDisplayLocalDataSource;
import xyz.kida.rawg.data.repository.mapper.GameToGameEntityMapper;
import xyz.kida.rawg.data.repository.remote.GameDisplayRemoteDataSource;

public class GameDisplayDataRepository implements GameDisplayRepository {

    private GameDisplayLocalDataSource gameDisplayLocalDataSource;
    private GameDisplayRemoteDataSource gameDisplayRemoteDataSource;
    private GameToGameEntityMapper gameMapper;

    public GameDisplayDataRepository(GameDisplayLocalDataSource gameDisplayLocalDataSource, GameDisplayRemoteDataSource gameDisplayRemoteDataSource, GameToGameEntityMapper gameMapper) {
        this.gameDisplayLocalDataSource = gameDisplayLocalDataSource;
        this.gameDisplayRemoteDataSource = gameDisplayRemoteDataSource;
        this.gameMapper = gameMapper;
    }

    @Override
    public Single<GameSearchResponse> getGames(String gameName) {
        return gameDisplayRemoteDataSource.getGameSearchResponse(gameName)
                .zipWith(gameDisplayLocalDataSource.getCollectionListId(), new BiFunction<GameSearchResponse, List<String>, GameSearchResponse>() {
                    @Override
                    public GameSearchResponse apply(GameSearchResponse gameSearchResponse, List<String> idList) throws Exception {
                        for (Game game : gameSearchResponse.getGames()) {
                            if (idList.contains(game.getId())) {
                                game.setFavorite();
                            }
                        }
                        return gameSearchResponse;
                    }
                });
    }

    @Override
    public Flowable<List<GameEntity>> loadFavorites() {
        return gameDisplayLocalDataSource.loadCollection();
    }

    @Override
    public Completable addGameToCollection(String gameId) {
        return gameDisplayRemoteDataSource.getGameDetails(gameId)
                .map(new Function<Game, GameEntity>() {
                    @Override
                    public GameEntity apply(Game game) throws Exception {
                        return gameMapper.toGameEntity(game);
                    }
                })
                .flatMapCompletable(new Function<GameEntity, CompletableSource>() {
                    @Override
                    public CompletableSource apply(GameEntity gameEntity) throws Exception {
                        return gameDisplayLocalDataSource.addGameToCollection(gameEntity);
                    }
                });
    }

    @Override
    public Completable removeGameFromFavorites(String gameId) {
        return gameDisplayLocalDataSource.deleteGameFromCollection(gameId);
    }

    @Override
    public Single<List<String>> getFavoriteGamesId() {
        return gameDisplayLocalDataSource.getCollectionListId();
    }

    @Override
    public Single<GameVideoSearchResponse> getVideosForFavoriteGames(String gameId) {
        test();
        return gameDisplayRemoteDataSource.getVideoSearchResponse(gameId);
    }

    public Single<List<GameVideoSearchResponse>> test() {
        return gameDisplayLocalDataSource.getCollectionListId()
                .flatMap(new Function<List<String>, SingleSource<List<GameVideoSearchResponse>>>() {
                    @Override
                    public SingleSource<List<GameVideoSearchResponse>> apply(List<String> strings) throws Exception {
                        return Flowable.fromIterable(strings)
                                .flatMapSingle(new Function<String, SingleSource<GameVideoSearchResponse>>() {
                                    @Override
                                    public SingleSource<GameVideoSearchResponse> apply(String s) throws Exception {
                                        return gameDisplayRemoteDataSource.getVideoSearchResponse(s);
                                    }
                                }).toList();
                    }
                });
    }
}
