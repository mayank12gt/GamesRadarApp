package com.example.gamesradar.network.freegames;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FreeGamesAPIClient {

    private static Retrofit retrofit;

    public static synchronized Retrofit getRetrofit(){
        if(retrofit==null){
            retrofit = new Retrofit.Builder().baseUrl("https://www.freetogame.com/api/")
                    .addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }

}
