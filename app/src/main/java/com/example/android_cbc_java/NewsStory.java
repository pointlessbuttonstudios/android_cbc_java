package com.example.android_cbc_java;

import com.google.gson.annotations.SerializedName;

public class NewsStory
{

    /*
     @PrimaryKey
    val id: String,

    @ColumnInfo(name="title")
    val title: String,

    @ColumnInfo(name="type")
    val type: String,

    @ColumnInfo(name="imageSmall")
    @Json(name = "imageSmall")
    val img_small: String

    * */
    @SerializedName("id")
    private String id;

    @SerializedName("title")
    private String title;

    @SerializedName("imageSmall")
    private String imageSmall;

    @SerializedName("type")
    private String type;

    public NewsStory()
    {

    }
    public NewsStory(String id, String title, String imgURL, String type)
    {
        this.id = id;
        this.title = title;
        this.imageSmall = imgURL;
        this.type = type;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getTitle()
    {
        return title;
    }

    public String getImageSmall()
    {
        return imageSmall;
    }

    public void setImageSmall(String imageSmall)
    {
        this.imageSmall = imageSmall;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }
}
