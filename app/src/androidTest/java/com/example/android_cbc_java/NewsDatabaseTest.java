package com.example.android_cbc_java;

import android.content.Context;

import androidx.room.Room;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.android_cbc_java.newsstory.NewsStory;
import com.example.android_cbc_java.newsstory.NewsStoryDao;
import com.example.android_cbc_java.newsstory.NewsStoryRoomDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class NewsDatabaseTest
{
    private NewsStoryDao newsStoryDao;
    private NewsStoryRoomDatabase newsStoryRoomDatabase;

    @Before
    public void createDb()
    {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        newsStoryRoomDatabase = Room.inMemoryDatabaseBuilder(context, NewsStoryRoomDatabase.class).allowMainThreadQueries().build();
        newsStoryDao = newsStoryRoomDatabase.newsStoryDao();
    }
    @After
    public void closeDb()
    {
        newsStoryRoomDatabase.close();
    }

    @Test
    public void insertAndGetType()
    {
        TypeAttributes typeAttributes = new TypeAttributes
                ("https://wyncode.co/uploads/2014/08/181.jpg",
                "https://pbs.twimg.com/media/EQ5qdaxXUAEosoF?format=jpg&name=large");
        NewsStory newsStory = new NewsStory("9000","Got a mask?",typeAttributes,"meme", "Dec 31st 2020");
        newsStoryDao.insert(newsStory);
        NewsStory retrieved = newsStoryDao.getNewsStory("9000");
        assertEquals(newsStory.getTypeAttributes().getImageLarge(), retrieved.getTypeAttributes().getImageLarge());
    }
}
