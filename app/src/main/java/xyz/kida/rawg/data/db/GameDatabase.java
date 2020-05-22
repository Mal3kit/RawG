package xyz.kida.rawg.data.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import xyz.kida.rawg.data.entity.GameEntity;

@Database(entities = {GameEntity.class}, version = 1)
public abstract class GameDatabase extends RoomDatabase {
    public abstract GameDao gameDao();
}
