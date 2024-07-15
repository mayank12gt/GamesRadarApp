package com.example.gamesradar.viewmodel;

import android.app.Application;
import android.util.Log;
import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.gamesradar.model.Radar.YTParser.Feed;
import com.example.gamesradar.network.ytparser.RetrofitClient;
import com.example.gamesradar.network.ytparser.RetrofitService;
import com.prof.rssparser.Article;
import com.prof.rssparser.Channel;
import com.prof.rssparser.OnTaskCompleted;
import com.prof.rssparser.Parser;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RadarFragmentViewModel extends AndroidViewModel {
    MutableLiveData<List<Pair<Article, Channel>>> data = new MutableLiveData<>();
    MutableLiveData<Boolean> isNewReleasesLoading = new MutableLiveData<>(true);
    MutableLiveData<Boolean> isNewReleasesError = new MutableLiveData<>(false);


    MutableLiveData<List<Pair<Article, Channel>>> reviewsData = new MutableLiveData<>();
    MutableLiveData<Boolean> isReviewsLoading = new MutableLiveData<>(true);
    MutableLiveData<Boolean> isReviewsError = new MutableLiveData<>(false);






    MutableLiveData<Feed> YTFeedData = new MutableLiveData<>();
    MutableLiveData<Boolean> isYTFeedLoading = new MutableLiveData<>(true);



    public RadarFragmentViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Pair<Article, Channel>>> getNewReleases(List<String> sources){
        isNewReleasesLoading.postValue(true);
        isNewReleasesError.postValue(false);
    //ExecutorService service = Executors.newSingleThreadExecutor();
    List<Pair<Article,Channel>> allPostsList= new ArrayList<>();
    Parser parser = new Parser.Builder().build();

    for (String source:sources) {
                parser.execute(source);
            }

            parser.onFinish(new OnTaskCompleted() {
                @Override
                public void onTaskCompleted(@NonNull Channel channel) {
                    for (Article article: channel.getArticles()) {
                        Log.d("data1",article.toString());
                        allPostsList.add(new Pair<>(article,channel));
                    }
                    data.postValue(allPostsList);
                    isNewReleasesLoading.postValue(false);

                }

                @Override
                public void onError(@NonNull Exception e) {
                    Log.d("Parse Error",e.getLocalizedMessage());
                    isNewReleasesError.postValue(true);
                    isNewReleasesLoading.postValue(false);

                }


            });





    return data;
}

    public LiveData<Boolean> getNewReleasesLoadingStatus(){return isNewReleasesLoading;}


        private int flag=0;

    public LiveData<List<Pair<Article, Channel>>> getReviews(List<String> sources){
        isReviewsLoading.postValue(true);
        isReviewsError.postValue(false);
        ExecutorService service = Executors.newSingleThreadExecutor();
        List<Pair<Article,Channel>> reviewPostList= new ArrayList<>();
        Parser parser = new Parser.Builder().build();
        Log.d("sources1",sources.toString());


                for (String source:sources) {
                    parser.execute(source);
                }

                parser.onFinish(new OnTaskCompleted() {
                    @Override
                    public void onTaskCompleted(@NonNull Channel channel) {
                        for (Article article: channel.getArticles()) {
                            //Log.d("data review",article.toString());
                            reviewPostList.add(new Pair<>(article,channel));

                        }

                        flag++;
                        Log.d("flag",String.valueOf(flag));
                            if (flag== sources.size()) {
                                Comparator<Pair<Article, Channel>> comp = new Comparator<Pair<Article, Channel>>() {
                                    @Override
                                    public int compare(Pair<Article, Channel> articleChannelPair, Pair<Article, Channel> t1) {
                                        return t1.first.getPubDate().compareTo(articleChannelPair.first.getPubDate());
                                    }
                                };


                                Collections.sort(reviewPostList, comp);

                                reviewsData.postValue(reviewPostList);
                                isReviewsLoading.postValue(false);
                            }



                    }

                    @Override
                    public void onError(@NonNull Exception e) {
                        Log.d("Parse Error",e.getLocalizedMessage());
                        isReviewsError.postValue(true);
                        isReviewsLoading.postValue(false);

                    }
                });













        return reviewsData;
    }

    public LiveData<Boolean> getReviewsLoadingStatus(){return isReviewsLoading;}





    public LiveData<Feed> getYTFeed(String Id){
        ExecutorService service = Executors.newSingleThreadExecutor();
        isYTFeedLoading.postValue(true);

        service.execute(new Runnable() {
            @Override
            public void run() {



                RetrofitService service1 = RetrofitClient.getRetrofit().create(RetrofitService.class);
                Log.d("here","ok");
                service1.getXmlData(Id).enqueue(new Callback<Feed>() {
                    @Override
                    public void onResponse(Call<Feed> call, Response<Feed> response) {
                        Log.d("here2","ok");
                        Feed feed = response.body();
                        YTFeedData.postValue(feed);
                        isYTFeedLoading.postValue(false);
                    }

                    @Override
                    public void onFailure(Call<Feed> call, Throwable t) {
                        Log.d("YTFeedErr",t.getLocalizedMessage());
                    }
                });




            }
        });
        return YTFeedData;
    }


    public LiveData<Boolean> getYTFeedLoadingStatus(){return isYTFeedLoading;}













}
