package xyz.kida.rawg.data.repository.mapper;

import xyz.kida.rawg.data.api.models.Game;
import xyz.kida.rawg.data.entity.GameEntity;

public class GameToGameEntityMapper {

    public GameEntity toGameEntity(Game game) {
        GameEntity gameEntity = new GameEntity();
        gameEntity.setId(game.getId());
        gameEntity.setGameName(game.getName());
        gameEntity.setImageUrl(game.getImageUrl());

        return gameEntity;
    }
}
