package com.example.android_cbc_java;

import android.content.SharedPreferences;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;

import com.bumptech.glide.request.RequestOptions;

import java.io.IOException;
public class Utility
{
    private static SharedPreferences sharedPreferences;
    /**
     * Basic internet connectivity check
     * @return true if there is internet connectivity
     */
    public static boolean isOnline()
    {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        }
        catch (IOException e)          { e.printStackTrace(); }
        catch (InterruptedException e) { e.printStackTrace(); }

        return false;
    }
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
}
