package com.example.android_cbc_java;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.example.android_cbc_java.newsstory.NewsStory;

import java.util.List;

public class NewsStoryViewModel extends AndroidViewModel
{
    private NewsRepository newsRepository;
    private LiveData<PagedList<NewsStory>> allNews;
    public NewsStoryViewModel(@NonNull Application application)
    {
        super(application);
        newsRepository = new NewsRepository(application);
        allNews = newsRepository.getAllNews();
    }
    public int getCount()
    {
        return newsRepository.getCount();
    }
    public LiveData<PagedList<NewsStory>> getAllNews()
    {
        return allNews;
    }
    public void insert(NewsStory newsStory)
    {
        newsRepository.insert(newsStory);
    }
    public void update(NewsStory newsStory)
    {
        newsRepository.update(newsStory);
    }
    public void insertAll(List<NewsStory> news)
    {
        newsRepository.insertAll(news);
    }
}
