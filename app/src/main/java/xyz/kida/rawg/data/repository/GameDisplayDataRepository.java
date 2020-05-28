package xyz.kida.rawg.data.repository;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import xyz.kida.rawg.data.api.models.Game;
import xyz.kida.rawg.data.api.models.GameSearchResponse;
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
    public Flowable<List<GameEntity>> loadCollection() {
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
    public Completable removeGameFromCollection(String gameId) {
        return gameDisplayLocalDataSource.deleteGameFromCollection(gameId);
    }
}
