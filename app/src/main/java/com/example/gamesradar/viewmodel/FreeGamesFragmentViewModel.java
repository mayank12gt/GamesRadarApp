package com.example.gamesradar.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.gamesradar.model.game.FreeGameDetails;
import com.example.gamesradar.model.game.FreeGame;
import com.example.gamesradar.repo.FreeGamesRepo;

import java.util.List;

public class FreeGamesFragmentViewModel extends AndroidViewModel {

    FreeGamesRepo repo;
    public FreeGamesFragmentViewModel(@NonNull Application application) {
        super(application);
        repo = new FreeGamesRepo();
    }

    public LiveData<List<FreeGame>> getFreeGames(String platform, String genre, String sortBy){
        return repo.getFreeGames(platform, genre, sortBy);
    }

    public LiveData<Boolean> getLoadingStatus(){return repo.getLoadingStatus();}

    public LiveData<FreeGameDetails> getGame(int gameId){
        return repo.getGame(gameId);
    }
    public LiveData<Boolean> getGameLoadingStatus(){return repo.getGameLoadingStatus();}


}
