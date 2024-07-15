package com.example.gamesradar.network.freegames;

import com.example.gamesradar.model.game.FreeGameDetails;
import com.example.gamesradar.model.game.FreeGame;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FreeGamesAPIService {
    @GET("games")
    Call<List<FreeGame>> getFreeGames(@Query("platform") String platform,
                                      @Query("category") String category,
                                      @Query("sort-by") String sortBy);

    @GET("game")
    Call<FreeGameDetails> getGame(@Query("id") int Id);
}
