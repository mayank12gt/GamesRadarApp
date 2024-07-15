package com.example.gamesradar.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.gamesradar.model.game.Giveaway;
import com.example.gamesradar.model.game.GiveawayDetails;
import com.example.gamesradar.repo.GiveawaysRepo;

import java.util.List;

public class GiveawaysFragmentViewModel extends AndroidViewModel {
    GiveawaysRepo repo;
    public GiveawaysFragmentViewModel(@NonNull Application application) {
        super(application);
        repo = new GiveawaysRepo();
    }

    public LiveData<List<Giveaway>> getAllGiveaways(String platform, String type, String sortBy){
        return repo.getAllGiveaways(platform, type, sortBy);
    }
    public LiveData<Boolean> getLoadingStatus(){return repo.getLoadingStatus();}
    public LiveData<Boolean> getErrorStatus(){return repo.getErrorStatus();}

    public LiveData<GiveawayDetails> getGiveaway(int GiveawayId){
        return repo.getGiveaway(GiveawayId);
    }
    public LiveData<Boolean> getGiveawayLoadingStatus(){return repo.getGiveawayLoadingStatus();}

}
