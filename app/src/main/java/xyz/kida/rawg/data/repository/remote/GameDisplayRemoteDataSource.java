package xyz.kida.rawg.data.repository.remote;

import io.reactivex.Single;
import xyz.kida.rawg.data.api.GameDisplayService;
import xyz.kida.rawg.data.api.models.Game;
import xyz.kida.rawg.data.api.models.GameSearchResponse;
import xyz.kida.rawg.data.api.models.GameVideoSearchResponse;

public class GameDisplayRemoteDataSource {

    private GameDisplayService gameDisplayService;

    public GameDisplayRemoteDataSource(GameDisplayService gameDisplayService) {
        this.gameDisplayService = gameDisplayService;
    }

    public Single<GameSearchResponse> getGameSearchResponse(String gameName) {
        return gameDisplayService.findGamesByName(gameName);
    }

    public Single<Game> getGameDetails(String gameId) {
        return gameDisplayService.getGame(gameId);
    }

    public Single<GameVideoSearchResponse> getVideoSearchResponse(String gameId) {
        return gameDisplayService.findVideoForGame(gameId);
    }
}
