package com.example.gamesradar.repo;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.gamesradar.database.SourcesDB;
import com.example.gamesradar.model.news.Source;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class NewsRepo {
Context context;
SourcesDB db;



    public NewsRepo(Context context) {
        this.context = context;
        db= SourcesDB.getDb(context);
    }

//    public LiveData<List<Source>> getFollowedSources(){
//        return db.getDao().getFollowedSources();
//    }

    public LiveData<List<Source>> getAllSourcesinDb(){
        return db.getDao().getAllSources();
    }



    public void addSource(Source source){
        db.getDao().addSource(source);
    }

    public void deleteSource(Source source){
        db.getDao().deleteSource(source);
    }

    public void updateSource(Source source){
        db.getDao().updateSource(source);
    }



//    public LiveData<Boolean> checkFollowed(Source source){
//       List<Source> followedSources = db.getDao().getFollowedSources().getValue();
//        MutableLiveData<Boolean> isFollowed = new MutableLiveData<>(false);
//
//        ExecutorService service = Executors.newSingleThreadExecutor();
//        service.execute(new Runnable() {
//            @Override
//            public void run() {
//                if(db.getDao().checkFollowed(source.getId())==1){
//                   isFollowed.postValue(true);
//                }
//                else {
//                    isFollowed.postValue(false);
//                }
//            }
//        });
//
//        return isFollowed;
//    }
//    public Boolean checkFollow(Source source){

//        ExecutorService service = Executors.newSingleThreadExecutor();
//        service.execute(new Runnable() {
//            @Override
//            public void run() {
//                if(db.getDao().checkFollowed(source.getId())==1){
//                isFollowed = true;
//                }
//                else {
//                    isFollowed = false;
//                }
//
//
//
//            }
//        });
//
//
//        return isFollowed;

//        ExecutorService service = Executors.newSingleThreadExecutor();
//        Future<Boolean> future = service.submit(new Callable<Boolean>() {
//            @Override
//            public Boolean call() throws Exception {
//                return db.getDao().checkFollowed(source.getId()) == 1;
//            }
//        });
//
//        try {
//            boolean isFollowed = future.get(); // This will block and wait for the result.
//            return isFollowed;
//        } catch (InterruptedException | ExecutionException e) {
//            Log.d("exc",e.getLocalizedMessage());
//            return false; // Or another appropriate default value
//        } finally {
//            service.shutdown();
//        }






//    public void getAllSources(){
//        Client client = new Client(context.getApplicationContext())
//                .setEndpoint("https://cloud.appwrite.io/v1")
//                .setProject("65213ef53d55a8e9f0c7");
//
//        Databases database = new Databases(client);
//        List<Source> sources = new ArrayList<>();
//
//        try {
//            database.listDocuments(
//                    "652141004668d9defd9b",
//                    "6521410a803fa4585ecc",new CoroutineCallback<>((result, error) -> {
//                        if (error != null) {
//                            error.printStackTrace();
//                            return;
//                        }
//                        for (int i=0;i<result.getTotal();i++){
//                            Source source = new Source();
//                            source.setId(result.getDocuments().get(i).getId());
//                            source.setName(result.getDocuments().get(i).getData().get("name").toString());
//                            source.setUrl(result.getDocuments().get(i).getData().get("url").toString());
//                            source.setImageUrl(result.getDocuments().get(i).getData().get("image_url").toString());
//                            Log.d("url",result.getDocuments().get(i).getData().get("url").toString());
//                            sources.add(source);
//                        }
////                        sourcesData.postValue(sources);
////                        isSourcesLoading.postValue(false);
//
//                    }));
//
//
//            List<Source> followedSources = new ArrayList<>();
//
//
//
//
//        } catch (AppwriteException e) {
//            Log.e("Appwrite", "Error: " + e.getLocalizedMessage());
//        }
//    }


    public void unfollowSource(Source source) {
        db.getDao().updateSource(source);
    }

    public void followSource(Source source) {
        db.getDao().updateSource(source);
    }


    public LiveData<List<Source>> getFollowedSources() {
        return db.getDao().getFollowedSources();
    }

    public boolean checkIfalreadyExists(String id) {
        ExecutorService service = Executors.newSingleThreadExecutor();
        Future<Boolean> doesExist = service.submit(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return db.getDao().checkIfalreadyExists(id);
            }
        });
        try {
            return doesExist.get();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        finally {
            service.shutdown();
        }
    }
}
