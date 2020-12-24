package com.example.android_cbc_java;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.util.Log;

import com.bumptech.glide.request.RequestOptions;
public class Utility
{
    public static Utility INSTANCE;
    private static SharedPreferences sharedPreferences;
    private static NewsStoryViewModel newsStoryViewModel;

    // singleton
    public static Utility init(Context context, NewsStoryViewModel newsStoryViewModel)
    {
        Log.d("Utility-->", "init()");
        if (INSTANCE == null)
        {
            synchronized (Utility.class)
            {
                if (INSTANCE == null)
                {
                    INSTANCE = new Utility(context, newsStoryViewModel);
                }
            }
        }
        return INSTANCE;
    }
    private Utility(Context context, NewsStoryViewModel newsStoryVM)
    {
        newsStoryViewModel = newsStoryVM;
    }
    /**
     * Basic internet connectivity check
     * @return true if there is internet connectivity
     */

    /**
     * Creates a black & white color filter to apply to images
     * @return bw color filter
     */
    public static ColorMatrixColorFilter transformBW()
    {
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0);
        return new ColorMatrixColorFilter(matrix);
    }
    /**
     * Creates options to customize image loads with Glide
     * @return requestOptions
     */
    public static RequestOptions imageHandler()
    {
        return new RequestOptions().placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground);
    }
    public static NewsStoryViewModel getNewsStoryViewModel()
    {
        return newsStoryViewModel;
    }
}
