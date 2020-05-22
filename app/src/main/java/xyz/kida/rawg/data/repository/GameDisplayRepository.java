package xyz.kida.rawg.data.repository;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import xyz.kida.rawg.data.api.models.GameSearchResponse;
import xyz.kida.rawg.data.entity.GameEntity;

public interface GameDisplayRepository {

    Single<GameSearchResponse> getGames(String gameName);

    Flowable<List<GameEntity>> loadCollection();

    Completable addGameToCollection(String gameId);

    Completable removeGameFromCollection(String gameId);
}
