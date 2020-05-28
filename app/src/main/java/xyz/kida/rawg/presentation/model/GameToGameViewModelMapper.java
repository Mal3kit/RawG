package xyz.kida.rawg.presentation.model;

import xyz.kida.rawg.data.api.models.Game;

public class GameToGameViewModelMapper {

    public GameViewModel toGameViewModel(Game game) {
        GameViewModel gameViewModel = new GameViewModel();
        gameViewModel.setId(game.getId());
        gameViewModel.setName(game.getName());
        gameViewModel.setRating(game.getRating() + "/5");
        gameViewModel.setImageUrl(game.getImageUrl());
        gameViewModel.setFavorite(game.isFavorite());

        return gameViewModel;
    }
}
