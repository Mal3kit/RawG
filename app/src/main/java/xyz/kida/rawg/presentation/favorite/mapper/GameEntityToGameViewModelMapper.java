package xyz.kida.rawg.presentation.favorite.mapper;

import xyz.kida.rawg.data.entity.GameEntity;
import xyz.kida.rawg.presentation.model.GameViewModel;

public class GameEntityToGameViewModelMapper {

    public GameViewModel toGameViewModel(GameEntity game) {
        GameViewModel gameViewModel = new GameViewModel();
        gameViewModel.setId(game.getId());
        gameViewModel.setName(game.getName());
        gameViewModel.setRating(game.getRating() + "/5");
        gameViewModel.setImageUrl(game.getImageUrl());

        return gameViewModel;
    }
}
