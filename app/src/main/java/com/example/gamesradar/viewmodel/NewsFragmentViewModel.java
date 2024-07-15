package com.example.gamesradar.viewmodel;

import android.app.Application;
import android.util.Log;
import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.gamesradar.model.news.Source;
import com.example.gamesradar.repo.NewsRepo;
import com.prof.rssparser.Article;
import com.prof.rssparser.Channel;
import com.prof.rssparser.OnTaskCompleted;
import com.prof.rssparser.Parser;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Handler;





public class NewsFragmentViewModel extends AndroidViewModel {



    MutableLiveData<List<Source>> sourcesData = new MutableLiveData<>();
    MutableLiveData<List<Source>> followedSources = new MutableLiveData<>();
    MutableLiveData<Boolean> isSourcesLoading  = new MutableLiveData<>(false);




    MutableLiveData<List<Pair<Article, Channel>>> feedPostsData = new MutableLiveData<>();
    MutableLiveData<Boolean> isFeedPostsLoading  = new MutableLiveData<>(false);



    MutableLiveData<List<Pair<Article, Channel>>> followedFeedPostsData = new MutableLiveData<>();
    MutableLiveData<Boolean> isFollowedFeedsPostsLoading  = new MutableLiveData<>(false);
    NewsRepo repo;
    public NewsFragmentViewModel(@NonNull Application application) {
        super(application);
        repo = new NewsRepo(application);
    }



//    public void getSourcesfromAppWrite(){
//        //isSourcesLoading.postValue(true);
//        Log.d("appwrite","fetching sources");
//        Log.d("isSourcesLoading","true");
//
//        Client client = new Client(getApplication())
//                .setEndpoint("https://cloud.appwrite.io/v1")
//                .setProject("65213ef53d55a8e9f0c7");
//
//        Databases database = new Databases(client);
//      //  List<Source> sourcesToAddtoDb = new ArrayList<>();
//
//        try {
//            database.listDocuments(
//                    "652141004668d9defd9b",
//                    "6521410a803fa4585ecc",new CoroutineCallback<>((result, error) -> {
//                        if (error != null) {
//                            error.printStackTrace();
//                            return;
//                        }
//
//                        for (int i=0;i<result.getTotal();i++) {
//                            Log.d("SourceResult",result.getDocuments().get(i).getData().get("name").toString()+" from appwrite");
//                            if (repo.checkIfalreadyExists(result.getDocuments().get(i).getId()) == false) {
//                                Log.d("Source",result.getDocuments().get(i).getData().get("name").toString()+" doesn't exists");
//
//                                Source source = new Source();
//                                source.setId(result.getDocuments().get(i).getId());
//                                source.setName(result.getDocuments().get(i).getData().get("name").toString());
//                                source.setUrl(result.getDocuments().get(i).getData().get("url").toString());
//                                source.setFollowed(false);
//                                source.setImageUrl(result.getDocuments().get(i).getData().get("image_url").toString());
//                                Log.d("url", result.getDocuments().get(i).getData().get("url").toString());
//                               // sourcesToAddtoDb.add(source);
//
//                                ExecutorService service = Executors.newSingleThreadExecutor();
//                                service.execute(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        repo.addSource(source);
//                                        Log.d("source",source.getName()+" added to db");
//                                    }
//                                });
//                            }
//                            else{
//                                Log.d("Source",result.getDocuments().get(i).getData().get("name").toString()+" already exists");
//
//                            }
//                        }
//
////                        for (Source s:
////                             sourcesToAddtoDb) {
////
////
////                        }
//
//                    }));
//
//
//        } catch (AppwriteException e) {
//            Log.e("Appwrite", "Error: " + e.getLocalizedMessage());
//        }
//
//
//    }




//    private boolean checkIfalreadyExists(String id, List<Source> sourceListinDb) {
//        if(sourceListinDb==null || sourceListinDb.isEmpty()){
//            Log.d("sourceListinDb","empty");
//            return false;
//        }
//        for (Source s :
//        sourceListinDb) {
//            if(s.getId().equals(id)){
//                return true;
//            }
//
//        }
//        return false;
//    }


    public LiveData<Boolean> getLoadingStatus(){return isSourcesLoading;}




    public LiveData<List<Source>> getFollowedSources() {
        return repo.getFollowedSources();
    }
    public LiveData<List<Pair<Article, Channel>>> getFollowedFeedPosts(List<Source> sources){
        Log.d("getFollowedFeedPosts","called");
        Log.d("getFollowedSources",sources.toString());
        List<Pair<Article,Channel>> postList = new ArrayList<>();

        ExecutorService service = Executors.newSingleThreadExecutor();
        Parser parser = new Parser.Builder().build();

        isFollowedFeedsPostsLoading.postValue(true);

        service.execute(new Runnable() {
                @Override
                public void run() {
                    Log.d("running","true");
                    if(!sources.isEmpty()) {
                        for (Source source : sources) {
                            Log.d("sources2", source.getName());
                            parser.execute(source.getUrl());
                        }
                    }
                    else{
                        isFollowedFeedsPostsLoading.postValue(false);
                       followedFeedPostsData.postValue(null);
                    }

                    parser.onFinish(new OnTaskCompleted() {
                        @Override
                        public void onTaskCompleted(@NonNull Channel channel) {
                            for (Article article : channel.getArticles()) {
                                //Log.d("data review",article.toString());
                                postList.add(new Pair<>(article, channel));

                            }


                            followedFeedPostsData.postValue(postList);
                            Log.d("postvalue","called");
                            isFollowedFeedsPostsLoading.postValue(false);

                        }

                        @Override
                        public void onError(@NonNull Exception e) {
                            Log.d("Parse Error", e.getLocalizedMessage());

                        }
                    });
                }
            });

        return followedFeedPostsData;
    }
    public LiveData<Boolean> getFollowedFeedsPostsLoadingStatus(){return isFollowedFeedsPostsLoading;}




    public void addSource(Source source){
        ExecutorService service= Executors.newSingleThreadExecutor();
        service.execute(new Runnable() {
            @Override
            public void run() {
                repo.addSource(source);
            }
        });
    }
    public void deleteSource(Source source){
        ExecutorService service= Executors.newSingleThreadExecutor();
        service.execute(new Runnable() {
            @Override
            public void run() {
                repo.deleteSource(source);
            }
        });
    }
    public void updateSource(Source source){
        ExecutorService service= Executors.newSingleThreadExecutor();
        service.execute(new Runnable() {
            @Override
            public void run() {
                repo.updateSource(source);
            }
        });
    }

    public LiveData<List<Source>>getAllSources(){
       // getSourcesfromAppWrite();
        isSourcesLoading.postValue(false);
        return repo.getAllSourcesinDb();
    }
    public LiveData<List<Pair<Article, Channel>>> getFeedPosts(String url){
        List<Pair<Article,Channel>> postList = new ArrayList<>();
        ExecutorService service = Executors.newSingleThreadExecutor();
        Parser parser = new Parser.Builder().build();
        isFeedPostsLoading.postValue(true);
        service.execute(new Runnable() {
            @Override
            public void run() {
               parser.execute(url);
               parser.onFinish(new OnTaskCompleted() {
                   @Override
                   public void onTaskCompleted(@NonNull Channel channel) {
                       for (Article article: channel.getArticles()) {
                           Log.d("data review",article.toString());
                           postList.add(new Pair<>(article,channel));

                       }
                       feedPostsData.postValue(postList);
                       isFeedPostsLoading.postValue(false);

                   }

                   @Override
                   public void onError(@NonNull Exception e) {
                       Log.d("Parse Error",e.getLocalizedMessage());

                   }
               });
            }
        });
        return feedPostsData;
    }

    public LiveData<Boolean> getFeedPostsLoadingStatus(){return isFeedPostsLoading;}





//    public LiveData<Boolean> checkFollowed(Source source){
//        return repo.checkFollowed(source);
//    }
//
//    public Boolean checkFollow(Source source){return repo.checkFollow(source);}
}
