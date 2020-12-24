package com.example.android_cbc_java.newsstory;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.android_cbc_java.Converters;
import com.example.android_cbc_java.TypeAttributes;
import com.google.gson.annotations.SerializedName;

@Entity(tableName="news_table")
@TypeConverters({Converters.class})
public class NewsStory implements Parcelable
{
    @NonNull
    @PrimaryKey
    @SerializedName("id")
    private String id;

    @ColumnInfo(name="title")
    @SerializedName("title")
    private String title;

    @ColumnInfo(name="typeAttributes")
    @SerializedName("typeAttributes")
    private TypeAttributes typeAttributes;

    @ColumnInfo(name="readablePublishedAt")
    @SerializedName("readablePublishedAt")
    private String readablePublishedAt;

    @ColumnInfo(name="type")
    @SerializedName("type")
    private String type;
    public NewsStory(String id, String title, TypeAttributes typeAttributes, String type, String readablePublishedAt)
    {
        this.id = id;
        this.title = title;
        this.typeAttributes = typeAttributes;
        this.type = type;
        this.readablePublishedAt = readablePublishedAt;
    }
    protected NewsStory(Parcel in)
    {
        id = in.readString();
        title = in.readString();
        readablePublishedAt = in.readString();
        type = in.readString();
    }
    public static final Creator<NewsStory> CREATOR = new Creator<NewsStory>()
    {
        @Override
        public NewsStory createFromParcel(Parcel in)
        {
            return new NewsStory(in);
        }
        @Override
        public NewsStory[] newArray(int size)
        {
            return new NewsStory[size];
        }
    };
    public String getId()
    {
        return id;
    }
    public void setId(String id)
    {
        this.id = id;
    }
    public void setTitle(String title)
    {
        this.title = title;
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
    @Override
    public int describeContents()
    {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel parcel, int i)
    {
        parcel.writeString(id);
        parcel.writeString(title);
        parcel.writeString(readablePublishedAt);
        parcel.writeString(type);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewsStory newsStory = (NewsStory) o;
        return id.equals(newsStory.id);
    }
}
