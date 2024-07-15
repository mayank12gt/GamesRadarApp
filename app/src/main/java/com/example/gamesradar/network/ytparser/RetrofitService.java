package com.example.gamesradar.network.ytparser;

import com.example.gamesradar.model.Radar.YTParser.Feed;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitService {
    @GET("videos.xml")
    Call<Feed> getXmlData(@Query("channel_id")String Id);
}
