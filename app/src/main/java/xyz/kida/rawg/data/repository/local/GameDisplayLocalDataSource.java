package xyz.kida.rawg.data.repository.local;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import xyz.kida.rawg.data.db.GameDatabase;
import xyz.kida.rawg.data.entity.GameEntity;

public class GameDisplayLocalDataSource {

    private GameDatabase gameDatabase;

    public GameDisplayLocalDataSource(GameDatabase gameDatabase) {
        this.gameDatabase = gameDatabase;
    }

    public Flowable<List<GameEntity>> loadFavorites() {
        return gameDatabase.gameDao().loadCollection();
    }

    public Completable addGameToFavorites(GameEntity gameEntity) {
        return gameDatabase.gameDao().addGameToCollection(gameEntity);
    }

    public Completable deleteGameFromFavorites(String gameId) {
        return gameDatabase.gameDao().deleteGameFromCollection(gameId);
    }

    public Single<List<String>> getCollectionListId() {
        return gameDatabase.gameDao().getCollectionListId();
    }

}
