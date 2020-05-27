package xyz.kida;

import android.app.Application;

import xyz.kida.rawg.data.di.FakeDependencyInjection;

public class RawGApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FakeDependencyInjection.setApplicationContext(this);
    }
}
