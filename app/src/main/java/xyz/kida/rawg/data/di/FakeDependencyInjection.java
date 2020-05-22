package xyz.kida.rawg.data.di;

import android.content.Context;

import androidx.room.Room;

import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import xyz.kida.rawg.data.api.GameDisplayService;
import xyz.kida.rawg.data.db.GameDatabase;
import xyz.kida.rawg.data.repository.GameDisplayDataRepository;
import xyz.kida.rawg.data.repository.GameDisplayRepository;
import xyz.kida.rawg.data.repository.local.GameDisplayLocalDataSource;
import xyz.kida.rawg.data.repository.mapper.GameToGameEntityMapper;
import xyz.kida.rawg.data.repository.remote.GameDisplayRemoteDataSource;

/**
 * As mention, NEVER do that in a production app. Ever.
 * Prefer using a DI framework, such as Dagger
 */
public class FakeDependencyInjection {

    private static GameDisplayService gameDisplayService;
    private static GameDisplayRepository gameDisplayRepository;
    private static GameDatabase gameDatabase;

    private static Context applicationContext;
    private static Gson gson;
    private static Retrofit retrofit;

    private static final String API_BASE_URL = "https://api.rawg.io/api/";

    public static GameDisplayRepository getGameDisplayRepository() {
        if (gameDisplayRepository == null) {
            gameDisplayRepository = new GameDisplayDataRepository(
                    new GameDisplayLocalDataSource(getGameDatabase()),
                    new GameDisplayRemoteDataSource(getGameDisplayService()),
                    new GameToGameEntityMapper()
            );
        }
        return gameDisplayRepository;
    }

    public static Gson getGson() {
        if (gson == null) {
            gson = new Gson();
        }
        return gson;
    }

    public static void setApplicationContext(Context context) {
        applicationContext = context;
    }

    public static GameDatabase getGameDatabase() {
        if (gameDatabase == null) {
            gameDatabase = Room.databaseBuilder(applicationContext,
                    GameDatabase.class, "game-database")
                    .build();
        }
        return gameDatabase;
    }

    public static GameDisplayService getGameDisplayService() {
        if (gameDisplayService == null) {
            gameDisplayService = getRetrofit().create(GameDisplayService.class);
        }
        return gameDisplayService;
    }

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(new OkHttpClient
                            .Builder()
                            .addInterceptor(new HttpLoggingInterceptor()
                                    .setLevel(HttpLoggingInterceptor.Level.BODY))
                            .build())
                    .build();
        }
        return retrofit;
    }
}
