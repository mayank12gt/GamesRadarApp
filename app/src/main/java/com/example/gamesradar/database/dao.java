package com.example.gamesradar.database;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.gamesradar.model.news.Source;

import java.util.List;

@androidx.room.Dao
public interface dao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addSource(Source source);

    @Delete
    void deleteSource(Source source);

    @Update
    public void updateSource(Source source);

    @Query("select * from sources order by id DESC ")
    LiveData<List<Source>> getAllSources();


@Query("SELECT 1 FROM sources WHERE id = :id")
    public int checkFollowed(String id);


    @Query("select * from sources where isFollowed = 1 order by id DESC ")
    LiveData<List<Source>> getFollowedSources();

    @Query("select 1 from sources where id = :id ")
    Boolean checkIfalreadyExists(String id);
}
