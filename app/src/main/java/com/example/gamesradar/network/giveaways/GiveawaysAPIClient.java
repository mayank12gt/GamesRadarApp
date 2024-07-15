package com.example.gamesradar.network.giveaways;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GiveawaysAPIClient {
    private static Retrofit retrofit;
    public static synchronized Retrofit getRetrofit(){
        if(retrofit==null){
            retrofit = new Retrofit.Builder().baseUrl("https://www.gamerpower.com/api/")
                    .addConverterFactory(GsonConverterFactory.create()).build();

        }
        return retrofit;
    }
}
