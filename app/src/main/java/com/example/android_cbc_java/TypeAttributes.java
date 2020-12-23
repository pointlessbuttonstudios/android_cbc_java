package com.example.android_cbc_java;

import com.google.gson.annotations.SerializedName;

public class TypeAttributes
{
    @SerializedName("imageSmall")
    private String imageSmall;

    @SerializedName("imageLarge")
    private String imageLarge;

    public TypeAttributes(String imageSmall, String imageLarge)
    {
        this.imageSmall = imageSmall;
        this.imageLarge = imageLarge;
    }
    public String getImageSmall()
    {
        return imageSmall;
    }
    public void setImageSmall(String imageSmall)
    {
        this.imageSmall = imageSmall;
    }
    public String getImageLarge()
    {
        return imageLarge;
    }
    public void setImageLarge(String imageLarge)
    {
        this.imageLarge = imageLarge;
    }

    @Override
    public String toString()
    {
        return "TypeAttributes" +
                "\n{" +
                "\nimageSmall='" + imageSmall + '\'' +
                "\nimageLarge='" + imageLarge + '\'' +
                '}';
    }
}
