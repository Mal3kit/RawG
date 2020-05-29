package xyz.kida.rawg.presentation.model;

import xyz.kida.rawg.data.api.models.GameVideo;

public class GameVideoToGameVideoViewModelMapper {

    public GameVideoViewModel toGameVideoViewModel(GameVideo gameVideo) {
        GameVideoViewModel gameVideoViewModel = new GameVideoViewModel();
        gameVideoViewModel.setId(gameVideo.getId());
        gameVideoViewModel.setName(gameVideo.getName());
        gameVideoViewModel.setChannelName(gameVideo.getChannelName());
        gameVideoViewModel.setViewCount(gameVideo.getViewCount());
        gameVideoViewModel.setImageUrl(gameVideo.getImageUrl());

        return gameVideoViewModel;
    }
}
