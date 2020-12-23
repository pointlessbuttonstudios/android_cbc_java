package com.example.android_cbc_java;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetDataService
{
    @GET("/aggregate_api/v1/items?lineupSlug=news")
    Call<List<NewsStory>> getAllNews();
}
