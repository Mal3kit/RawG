package xyz.kida.rawg.data.api.models;

import com.google.gson.annotations.Expose;

import java.util.List;

public class GameSearchResponse {

    @Expose
    private List<Game> games;

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }
}
