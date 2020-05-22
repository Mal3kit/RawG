package xyz.kida.rawg.data.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import xyz.kida.rawg.data.entity.GameEntity;

@Dao
public interface GameDao {

    @Query("SELECT * FROM gameentity")
    Flowable<List<GameEntity>> loadCollection();

    @Insert
    Completable addGameToCollection(GameEntity gameEntity);

    @Query("DELETE FROM gameentity WHERE id = :id")
    Completable deleteGameFromCollection(String id);

    @Query("SELECT id FROM gameentity")
    Single<List<String>> getCollectionListId();
}
