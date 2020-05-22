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
import xyz.kida.rawg.data.repository.local.GameLocalDataSource;
import xyz.kida.rawg.data.repository.mapper.GameToGameEntityMapper;
import xyz.kida.rawg.data.repository.remote.GameRemoteDataSource;

public class GameDataRepository implements GameRepository {

    private GameLocalDataSource gameLocalDataSource;
    private GameRemoteDataSource gameRemoteDataSource;
    private GameToGameEntityMapper gameMapper;

    public GameDataRepository(GameLocalDataSource gameLocalDataSource, GameRemoteDataSource gameRemoteDataSource, GameToGameEntityMapper gameMapper) {
        this.gameLocalDataSource = gameLocalDataSource;
        this.gameRemoteDataSource = gameRemoteDataSource;
        this.gameMapper = gameMapper;
    }

    @Override
    public Single<GameSearchResponse> getGames(String gameName) {
        return gameRemoteDataSource.getGameSearchResponse(gameName)
                .zipWith(gameLocalDataSource.getCollectionListId(), new BiFunction<GameSearchResponse, List<String>, GameSearchResponse>() {
                    @Override
                    public GameSearchResponse apply(GameSearchResponse gameSearchResponse, List<String> idList) throws Exception {
                        for (Game game : gameSearchResponse.getGames()) {
                            if (idList.contains(game.getId())) {
                                game.setFavourite(true);
                            }
                        }
                        return gameSearchResponse;
                    }
                });
    }

    @Override
    public Flowable<List<GameEntity>> loadCollection() {
        return gameLocalDataSource.loadCollection();
    }

    @Override
    public Completable addGameToCollection(String gameId) {
        return gameRemoteDataSource.getGameDetails(gameId)
                .map(new Function<Game, GameEntity>() {
                    @Override
                    public GameEntity apply(Game game) throws Exception {
                        return gameMapper.toGameEntity(game);
                    }
                })
                .flatMapCompletable(new Function<GameEntity, CompletableSource>() {
                    @Override
                    public CompletableSource apply(GameEntity gameEntity) throws Exception {
                        return gameLocalDataSource.addGameToCollection(gameEntity);
                    }
                });
    }

    @Override
    public Completable removeGameFromCollection(String gameId) {
        return gameLocalDataSource.deleteGameFromCollection(gameId);
    }
}
