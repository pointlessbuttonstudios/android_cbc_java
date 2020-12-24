package com.example.android_cbc_java;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.android_cbc_java.newsstory.NewsStory;
import com.example.android_cbc_java.newsstory.NewsStoryDao;
import com.example.android_cbc_java.newsstory.NewsStoryRoomDatabase;

import java.util.List;

public class NewsRepository
{
    private NewsStoryDao newsStoryDao;
    private LiveData<PagedList<NewsStory>> allNews;

    public NewsRepository(Application application)
    {
        NewsStoryRoomDatabase newsStoryRoomDatabase = NewsStoryRoomDatabase.getDatabase(application);
        newsStoryDao = newsStoryRoomDatabase.newsStoryDao();
        allNews = new LivePagedListBuilder<>(
                newsStoryDao.getAllNews(), /* page size */ 5).build();
    }
    public int getCount()
    {
        return newsStoryDao.getCount();
    }
    NewsStory[] getNewsOfType(String someType)
    {
        return newsStoryDao.getNewsOfType(someType);
    }
    NewsStory[] getNewsStory(String id)
    {
        return newsStoryDao.getNewsStory();
    }
    LiveData<PagedList<NewsStory>> getAllNews()
    {
        return allNews;
    }
    public NewsStory[] getAnyNewsStory()
    {
        return newsStoryDao.getNewsStory();
    }
    public void insert(NewsStory news)
    {
        new insertAsyncTask(newsStoryDao).execute(news);
    }

    public void insertAll(List<NewsStory> news)
    {
        new insertAllAsyncTask(newsStoryDao).execute(news);
    }
    private static class insertAllAsyncTask extends AsyncTask<List<NewsStory>, Void, Void>
    {
        private NewsStoryDao newsStoryDao;
        insertAllAsyncTask(NewsStoryDao dao)
        {
            newsStoryDao = dao;
        }
        @Override
        protected Void doInBackground(final List<NewsStory>... stories)
        {
            for(NewsStory ns : stories[0])
            {
                newsStoryDao.insert(ns);
            }
            return null;
        }
    }

    private static class insertAsyncTask extends AsyncTask<NewsStory, Void, Void>
    {
        private NewsStoryDao newsStoryDao;
        insertAsyncTask(NewsStoryDao dao)
        {
            newsStoryDao = dao;
        }
        @Override
        protected Void doInBackground(final NewsStory... stories)
        {
            newsStoryDao.insert(stories[0]);
            return null;
        }
    }
    public void update(NewsStory news)
    {
        new updateAsyncTask(newsStoryDao).execute(news);
    }
    private static class updateAsyncTask extends AsyncTask<NewsStory, Void, Void>
    {
        private NewsStoryDao newsStoryDao;
        updateAsyncTask(NewsStoryDao dao)
        {
            newsStoryDao = dao;
        }
        @Override
        protected Void doInBackground(final NewsStory... stories)
        {
            newsStoryDao.update(stories[0]);
            return null;
        }
    }
}
