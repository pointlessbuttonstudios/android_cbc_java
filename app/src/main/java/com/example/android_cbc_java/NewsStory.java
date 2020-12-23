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

    @SerializedName("typeAttributes")
    private TypeAttributes typeAttributes;

    @SerializedName("readablePublishedAt")
    private String readablePublishedAt;

    @SerializedName("type")
    private String type;

    public NewsStory()
    {

    }
    public NewsStory(String id, String title, TypeAttributes typeAttributes, String type, String readablePublishedAt)
    {
        this.id = id;
        this.title = title;
        this.typeAttributes = typeAttributes;
        this.type = type;
        this.readablePublishedAt = readablePublishedAt;
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
    public String getType()
    {
        return type;
    }
    public void setType(String type)
    {
        this.type = type;
    }
    public String getReadablePublishedAt()
    {
        return readablePublishedAt;
    }
    public void setReadablePublishedAt(String readablePublishedAt)
    {
        this.readablePublishedAt = readablePublishedAt;
    }
    public TypeAttributes getTypeAttributes()
    {
        return typeAttributes;
    }
    public void setTypeAttributes(TypeAttributes typeAttributes)
    {
        this.typeAttributes = typeAttributes;
    }

    @Override
    public String toString()
    {
        return "NewsStory" +
                "\n{" +
                "\nid='" + id + '\'' +
                "\ntitle='" + title + '\'' +
                "\ntypeAttributes=" + typeAttributes.toString() +
                "\nreadablePublishedAt='" + readablePublishedAt + '\'' +
                "\ntype='" + type + '\'' +
                '}';
    }
}
