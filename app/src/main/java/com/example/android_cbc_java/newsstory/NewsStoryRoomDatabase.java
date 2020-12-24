package com.example.android_cbc_java.newsstory;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.android_cbc_java.Converters;

@Database(entities = {NewsStory.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class NewsStoryRoomDatabase extends RoomDatabase
{
    private static Context con;
    private static NewsStoryRoomDatabase INSTANCE;
    public static NewsStoryRoomDatabase getDatabase(final Context context)
    {
        if (INSTANCE == null)
        {
            synchronized (NewsStoryRoomDatabase.class)
            {
                if (INSTANCE == null)
                {
                    // create database here
                    con = context;
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            NewsStoryRoomDatabase.class, "news_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    public abstract NewsStoryDao newsStoryDao();
}
