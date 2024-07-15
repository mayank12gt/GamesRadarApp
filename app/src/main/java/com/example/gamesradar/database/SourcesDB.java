package com.example.gamesradar.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.gamesradar.MainActivity;
import com.example.gamesradar.model.news.Source;

import java.util.concurrent.Executors;

@Database(entities = {Source.class},version = 1)
public abstract class SourcesDB extends RoomDatabase {

private static SourcesDB INSTANCE;

public static synchronized SourcesDB getDb(Context context){
    if(INSTANCE==null){
        INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        SourcesDB.class,"sources_db").addCallback(roomCallback).build();
    }
    return INSTANCE;
}

private static RoomDatabase.Callback roomCallback = new Callback() {
    @Override
    public void onCreate(@NonNull SupportSQLiteDatabase db) {
        super.onCreate(db);
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                dao dao = INSTANCE.getDao();
                dao.addSource(new Source("Gameranx","https://gameranx.com/feed/","https://logo.clearbit.com/gameranx.com" ));
            }
        });
    }

    @Override
    public void onOpen(@NonNull SupportSQLiteDatabase db) {
        super.onOpen(db);
    }
};

public abstract dao getDao();

}
