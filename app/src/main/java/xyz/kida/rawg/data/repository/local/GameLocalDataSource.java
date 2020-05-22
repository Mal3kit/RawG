package xyz.kida.rawg.data.repository.local;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import xyz.kida.rawg.data.db.GameDatabase;
import xyz.kida.rawg.data.entity.GameEntity;

public class GameLocalDataSource {

    private GameDatabase gameDatabase;

    public GameLocalDataSource(GameDatabase gameDatabase) {
        this.gameDatabase = gameDatabase;
    }

    public Flowable<List<GameEntity>> loadCollection() {
        return gameDatabase.gameDao().loadCollection();
    }

    public Completable addGameToCollection(GameEntity gameEntity) {
        return gameDatabase.gameDao().addGameToCollection(gameEntity);
    }

    public Completable deleteGameFromCollection(String gameId) {
        return gameDatabase.gameDao().deleteGameFromCollection(gameId);
    }

    public Single<List<String>> getCollectionListId() {
        return gameDatabase.gameDao().getCollectionListId();
    }

}
