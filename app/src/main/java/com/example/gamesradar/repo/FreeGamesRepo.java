package com.example.gamesradar.repo;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.gamesradar.model.game.FreeGameDetails;
import com.example.gamesradar.model.game.FreeGame;
import com.example.gamesradar.network.freegames.FreeGamesAPIClient;
import com.example.gamesradar.network.freegames.FreeGamesAPIService;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FreeGamesRepo {
private FreeGamesAPIService apiService;
    MutableLiveData<Boolean> isLoading = new MutableLiveData<>(true);
    MutableLiveData<Boolean> isGameLoading = new MutableLiveData<>(true);
public FreeGamesRepo(){
   apiService = FreeGamesAPIClient.getRetrofit().create(FreeGamesAPIService.class);
}

public LiveData<List<FreeGame>> getFreeGames(String platform, String genre, String sortBy){
    isLoading.postValue(true);
    MutableLiveData<List<FreeGame>> liveData = new MutableLiveData<>();

    ExecutorService service = Executors.newSingleThreadExecutor();
    service.execute(new Runnable() {
        @Override
        public void run() {
            apiService.getFreeGames(platform,genre,sortBy).enqueue(new Callback<List<FreeGame>>() {
                @Override
                public void onResponse(Call<List<FreeGame>> call, Response<List<FreeGame>> response) {
                    liveData.postValue(response.body());
                    isLoading.postValue(false);
                }

                @Override
                public void onFailure(Call<List<FreeGame>> call, Throwable t) {
                    Log.d("Network Error",t.getLocalizedMessage());
                }
            });
        }
    });

    return liveData;
}
public LiveData<Boolean> getLoadingStatus(){return isLoading;}

    public LiveData<FreeGameDetails> getGame(int gameId){
        MutableLiveData<FreeGameDetails> gameLiveData = new MutableLiveData<>();
        isGameLoading.postValue(true);


        ExecutorService service = Executors.newSingleThreadExecutor();
     service.execute(new Runnable() {
         @Override
         public void run() {
             apiService.getGame(gameId).enqueue(new Callback<FreeGameDetails>() {
                 @Override
                 public void onResponse(Call<FreeGameDetails> call, Response<FreeGameDetails> response) {
                     gameLiveData.postValue(response.body());
                     isGameLoading.postValue(false);
                 }

                 @Override
                 public void onFailure(Call<FreeGameDetails> call, Throwable t) {
                     Log.d("Network Error",t.getLocalizedMessage());
                 }
             });
         }
     });


     return gameLiveData;
     };

    public LiveData<Boolean> getGameLoadingStatus(){return isGameLoading;}



}
