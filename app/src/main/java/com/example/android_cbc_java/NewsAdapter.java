package com.example.android_cbc_java;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.android_cbc_java.newsstory.NewsStory;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder>
{
    private List<NewsStory> newsStories;
    private Context context;

    public NewsAdapter(Context context, List<NewsStory> data){
        this.context = context;
        this.newsStories = data;
    }

    class NewsViewHolder extends RecyclerView.ViewHolder
    {

        public final View view;
        TextView headline;
        TextView date;
        private ImageView image;

        NewsViewHolder(View itemView)
        {
            super(itemView);
            view = itemView;
            date = view.findViewById(R.id.date);
            headline = view.findViewById(R.id.headline);
            image = view.findViewById(R.id.image);
        }
    }
    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_news_story, parent, false);
        return new NewsViewHolder(view);
    }
    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position)
    {
        NewsStory newsStory = newsStories.get(position);
        Log.d(this.getClass().getSimpleName(), newsStory.getTitle());
        holder.headline.setText(newsStory.getTitle());
        holder.date.setText(newsStory.getReadablePublishedAt());

        //Log.d(this.getClass().getSimpleName(), newsStory.getImageSmall());
        Glide.with(context).load(newsStory.getTypeAttributes().getImageSmall())
                .apply(Utility.imageHandler())
                .into(holder.image);
    }
    @Override
    public int getItemCount()
    {
        return newsStories.size();
    }
}