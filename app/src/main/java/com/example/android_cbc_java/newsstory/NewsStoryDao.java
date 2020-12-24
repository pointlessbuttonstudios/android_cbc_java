package com.example.android_cbc_java.newsstory;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface NewsStoryDao
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(NewsStory newsStory);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(NewsStory newsStory);

    @Query("SELECT * FROM news_table WHERE type =:someType")
    NewsStory[] getNewsOfType(String someType);

    /*
    // The Integer type parameter tells Room to use a
    // PositionalDataSource object.
    @Query("SELECT * FROM concerts ORDER BY date DESC")
    DataSource.Factory<Integer, Concert> concertsByDate();
    * */
    @Query("SELECT * FROM news_table")
    DataSource.Factory<Integer, NewsStory> getAllNews();

    @Query("SELECT * FROM news_table WHERE id=:id")
    NewsStory getNewsStory(String id);

    @Query("SELECT * FROM news_table LIMIT 1")
    NewsStory[] getNewsStory();

    @Query("DELETE FROM news_table")
    void deleteAll();

    @Delete
    void deleteNewsStory(NewsStory newsStory);

    @Query("SELECT count(*) FROM news_table")
    int getCount();
}
