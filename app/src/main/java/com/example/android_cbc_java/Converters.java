package com.example.android_cbc_java;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class Converters
{
    public static Gson gson = new Gson();
    @TypeConverter
    public static List<Integer> stringToInteger(String data)
    {
        if(data == null)
        {
            return Collections.emptyList();
        }
        Type listType = new TypeToken<List<Integer>>(){}.getType();
        return gson.fromJson(data, listType);
    }
    @TypeConverter
    public static String IntegerToString(List<Integer> integerObjects)
    {
        return gson.toJson(integerObjects);
    }
    @TypeConverter
    public static TypeAttributes stringToTypeAttributes(String data)
    {
        Type objectType = new TypeToken<TypeAttributes>(){}.getType();
        return gson.fromJson(data, objectType);
    }
    @TypeConverter
    public static String TypeAttributesToString(TypeAttributes data)
    {
        return gson.toJson(data);
    }
}

