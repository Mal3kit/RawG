package xyz.kida.rawg.data.api;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import xyz.kida.rawg.data.api.models.Game;
import xyz.kida.rawg.data.api.models.GameSearchResponse;

public interface GameDisplayService {

    @GET("games")
    Single<GameSearchResponse> findGamesByName(@Query("search") String gameName);

    @GET("games/{gameId}")
    Single<Game> getGame(@Path("gameId") String gameId);
}
