package com.example.gamesradar.repo;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.gamesradar.model.game.Giveaway;
import com.example.gamesradar.model.game.GiveawayDetails;
import com.example.gamesradar.network.giveaways.GiveawaysAPIClient;
import com.example.gamesradar.network.giveaways.GiveawaysAPIService;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GiveawaysRepo {
    GiveawaysAPIService apiService;
    MutableLiveData<Boolean> isLoading = new MutableLiveData<>(true);
    MutableLiveData<Boolean> isError = new MutableLiveData<>(false);

    MutableLiveData<Boolean> isGiveawayLoading = new MutableLiveData<>(true);

    public GiveawaysRepo() {
        this.apiService = GiveawaysAPIClient.getRetrofit().create(GiveawaysAPIService.class);
    }

    public LiveData<List<Giveaway>> getAllGiveaways(String platform, String type, String sortBy){
        MutableLiveData<List<Giveaway>> data=  new MutableLiveData<>();
        isLoading.postValue(true);
        isError.postValue(false);
//        apiService.getAllGiveaways(platform, type, sortBy).enqueue(new Callback<Object>() {
//            @Override
//            public void onResponse(Call<Object> call, Response<Object> response) {
//                Log.d("Data","here");
//                if (response.code()==200) {
//                    //Log.d("data",response.body().toString());
//                    //data.postValue((List<Giveaway>) response.body());
//                    List<Giveaway> list = (List<Giveaway>) response.body();
//                    Log.d("list",list.get(0).toString());
//                   isLoading.postValue(false);
//
//                } else if (response.code()==201) {
//                    Log.d("data","empty");
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Object> call, Throwable t) {
//                Log.d("Network Error",t.getLocalizedMessage());
//                Log.d("Network Error",t.getMessage());
//            }
//        });
        
        apiService.getAllGiveaways(platform,type,sortBy).enqueue(new Callback<List<Giveaway>>() {
            @Override
            public void onResponse(Call<List<Giveaway>> call, Response<List<Giveaway>> response) {
                if(response.isSuccessful()) {
                    Log.d("data", response.toString());
                } else if (!response.isSuccessful()) {
                    Log.d("data","empty");

                }
                data.postValue(response.body());
                isError.postValue(false);


                isLoading.postValue(false);
            }

            @Override
            public void onFailure(Call<List<Giveaway>> call, Throwable t) {
                Log.d("Network Error",t.getLocalizedMessage());
                Log.d("Network Error",t.getMessage());

                isError.postValue(true);
                isLoading.postValue(false);



            }
        });
return data;
    }


    public LiveData<GiveawayDetails> getGiveaway(int giveawayId) {
        MutableLiveData<GiveawayDetails> giveawayLivedata = new MutableLiveData<>();
       isGiveawayLoading.postValue(true);
//        isError.postValue(false);

        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(new Runnable() {
            @Override
            public void run() {
                apiService.getGiveaway(giveawayId).enqueue(new Callback<GiveawayDetails>() {
                    @Override
                    public void onResponse(Call<GiveawayDetails> call, Response<GiveawayDetails> response) {
                        giveawayLivedata.postValue(response.body());
                        isGiveawayLoading.postValue(false);
                    }

                    @Override
                    public void onFailure(Call<GiveawayDetails> call, Throwable t) {
                        Log.d("Network Error",t.getLocalizedMessage());

                    }
                });
            }
        });
return giveawayLivedata;

    }
    public LiveData<Boolean> getLoadingStatus(){return isLoading;}
    public LiveData<Boolean> getGiveawayLoadingStatus(){return isGiveawayLoading;}
    public LiveData<Boolean> getErrorStatus(){return isError;}
}
