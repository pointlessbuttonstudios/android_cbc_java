package com.example.android_cbc_java;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.android_cbc_java.newsstory.NewsStory;

import java.util.List;

public class NewsAdapter extends PagedListAdapter<NewsStory, NewsAdapter.NewsViewHolder>
{
    private static DiffUtil.ItemCallback<NewsStory> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<NewsStory>()
            {
                @Override
                public boolean areItemsTheSame(NewsStory oldStory, NewsStory newsStory)
                {
                    return oldStory.getId() == newsStory.getId();
                }
                @Override
                public boolean areContentsTheSame(NewsStory oldStory, NewsStory newStory)
                {
                    return oldStory.equals(newStory);
                }
            };

    private List<NewsStory> newsStories;
    private Context context;
    public NewsAdapter(Context context, List<NewsStory> data)
    {
        super(DIFF_CALLBACK);
        this.context = context;
        this.newsStories = data;
    }
    class NewsViewHolder extends RecyclerView.ViewHolder
    {
        public View view;
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
        public void clear()
        {
            view = null;
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
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        NewsStory newsStory = newsStories.get(position);
        if (newsStory != null) 
        {
            Log.d(this.getClass().getSimpleName(), newsStory.getTitle());
            holder.headline.setText(newsStory.getTitle());
            try
            {
                String justTheDate = newsStory.getReadablePublishedAt().split(",")[0];
                holder.date.setText(justTheDate);
            }
            catch (Exception e)
            {
                holder.date.setText(newsStory.getReadablePublishedAt());
                e.printStackTrace();
                Log.e(getClass().getSimpleName(), e.getMessage());
            }
            Glide.with(context).load(newsStory.getTypeAttributes().getImageLarge())
                    .apply(Utility.imageHandler())
                    .into(holder.image);
        } 
        else 
        {
            // Null defines a placeholder item - PagedListAdapter automatically
            // invalidates this row when the actual object is loaded from the
            // database.
            holder.clear();
        }
    }
    /*
    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position)
    {
        NewsStory newsStory = newsStories.get(position);
        Log.d(this.getClass().getSimpleName(), newsStory.getTitle());
        holder.headline.setText(newsStory.getTitle());
        try
        {
            String justTheDate = newsStory.getReadablePublishedAt().split(",")[0];
            holder.date.setText(justTheDate);
        }
        catch (Exception e)
        {
            holder.date.setText(newsStory.getReadablePublishedAt());
            e.printStackTrace();
            Log.e(getClass().getSimpleName(), e.getMessage());
        }
        Glide.with(context).load(newsStory.getTypeAttributes().getImageLarge())
                .apply(Utility.imageHandler())
                .into(holder.image);
    }*/
    @Override
    public int getItemCount()
    {
        if(newsStories != null)
        {
            return newsStories.size();
        }
        else return 0;
    }

    public void updateRecyclerData(List<NewsStory> data)
    {
        newsStories = data;
        notifyDataSetChanged();
    }
    public List<NewsStory> getNewsStories()
    {
        return newsStories;
    }
}