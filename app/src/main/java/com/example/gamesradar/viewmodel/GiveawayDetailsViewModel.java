package com.example.gamesradar.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.gamesradar.model.game.GiveawayDetails;
import com.example.gamesradar.repo.GiveawaysRepo;

public class GiveawayDetailsViewModel extends AndroidViewModel {

    GiveawaysRepo repo;
    public GiveawayDetailsViewModel(@NonNull Application application) {
        super(application);
        repo = new GiveawaysRepo();
    }

    public LiveData<GiveawayDetails> getGiveaway(int GiveawayId){
        return repo.getGiveaway(GiveawayId);
    }
    public LiveData<Boolean> getGiveawayLoadingStatus(){return repo.getGiveawayLoadingStatus();}

}
