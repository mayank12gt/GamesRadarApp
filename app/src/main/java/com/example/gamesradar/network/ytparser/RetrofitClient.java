package com.example.gamesradar.network.ytparser;

import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit;

    public static synchronized Retrofit getRetrofit(){
        if(retrofit==null){
            retrofit = new Retrofit.Builder().baseUrl("https://www.youtube.com/feeds/")
                    .addConverterFactory(SimpleXmlConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
