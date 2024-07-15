package com.example.gamesradar.network.giveaways;

import com.example.gamesradar.model.game.Giveaway;
import com.example.gamesradar.model.game.GiveawayDetails;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GiveawaysAPIService {
    @GET("giveaways")
    Call<List<Giveaway>> getAllGiveaways(@Query("platform") String platform,
                                         @Query("type") String category,
                                         @Query("sort-by") String sortBy);

//    @GET("giveaways")
//    Call<Object> getAllGiveaways(@Query("platform") String platform,
//                                  @Query("type") String category,
//                                  @Query("sort-by") String sortBy);


    @GET("giveaway")
    Call<GiveawayDetails> getGiveaway(@Query("id") int Id);


}
