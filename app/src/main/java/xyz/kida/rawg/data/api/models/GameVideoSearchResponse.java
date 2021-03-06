package xyz.kida.rawg.data.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GameVideoSearchResponse {

    public GameVideoSearchResponse() {
        this.gameVideos = new ArrayList<>();
    }

    @SerializedName("results")
    @Expose
    private List<GameVideo> gameVideos;

    public List<GameVideo> getGameVideos() {
        return gameVideos;
    }

    public void setGameVideos(List<GameVideo> gameVideos) {
        this.gameVideos = gameVideos;
    }
}
