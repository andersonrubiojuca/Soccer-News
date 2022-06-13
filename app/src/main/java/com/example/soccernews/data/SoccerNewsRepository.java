package com.example.soccernews.data;

import com.example.soccernews.data.remote.SoccerNewsAPI;
import com.example.soccernews.data.local.SoccerNewsDb;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import androidx.room.Room;
import com.example.soccernews.App;

public class SoccerNewsRepository {

    //region Constantes
    private static final String REMOTE_API_URL = "https://digitalinnovationone.github.io/soccer-news-api/";
    private static final String LOCAL_DB_NAME = "soccer-news";
    //endregion

    //region Atributos: encapsulam o acesso a nossa API (Retrofit) e banco de dados local (Room).
    private SoccerNewsAPI remoteApi;
    private SoccerNewsDb localDb;

    public SoccerNewsAPI getRemoteApi() {
        return remoteApi;
    }

    public SoccerNewsDb getLocalDb() {
        return localDb;
    }
    //endregion

    //region Singleton: garante uma instância única dos atributos relacionados ao Retrofit e Room.
    private SoccerNewsRepository () {
        remoteApi = new Retrofit.Builder()
                .baseUrl(REMOTE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(SoccerNewsAPI.class);

        localDb = Room.databaseBuilder(App.getInstance(), SoccerNewsDb.class, LOCAL_DB_NAME).allowMainThreadQueries().build();
    }

    public static SoccerNewsRepository getInstance() {
        return LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static final SoccerNewsRepository INSTANCE = new SoccerNewsRepository();
    }
    //endregion
}