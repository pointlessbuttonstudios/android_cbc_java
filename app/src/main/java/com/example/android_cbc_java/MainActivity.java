package com.example.android_cbc_java;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_cbc_java.network.RetrofitClientInstance;
import com.example.android_cbc_java.newsstory.NewsStory;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
{
    private LinearLayout offline_message_holder;
    private boolean continueOffline = false;
    private Handler handler = new Handler();
    private boolean isConnected = false;
    private CoordinatorLayout coordinatorLayout;
    private boolean filterActivated = false;
    private NewsAdapter newsAdapter;
    private RecyclerView recyclerView;
    private ProgressDialog progressDialog;
    private NewsStoryViewModel newsStoryViewModel;
    private String [] typesArray;
    private List<NewsStory> originalNews = new ArrayList<>();
    private AlertDialog alertDialog;
    private Runnable checkConstantly;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        offline_message_holder = findViewById(R.id.offline_message_holder);
        coordinatorLayout = findViewById(R.id.coordinatorlayout);
        recyclerView = findViewById(R.id.news_recyclerview);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("CBC News");
        setSupportActionBar(toolbar);

         newsStoryViewModel = ViewModelProviders.of(this).get(NewsStoryViewModel.class);
        // Create Utility Singleton
        Utility.init(this, newsStoryViewModel);

        progressDialog = new ProgressDialog(MainActivity.this);

        alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("No Internet Connectivity");
        alertDialog.setMessage("Can you check if you're connected?");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "RETRY",
                (dialog, which) ->
                {
                    dialog.dismiss();
                    refreshActivity();
                });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "CONTINUE OFFLINE",
                (dialog, which) ->
                {
                    dialog.dismiss();
                    continueOffline = true;

                    // display something showing you're offline
                    offline_message_holder.setVisibility(View.VISIBLE);

                    // setup from cache previously built from last pull if there's anything
                    generateDataList(null);
                });
        // only continue to check for an internet connection when necessary
        checkConstantly = new Runnable()
        {
            @Override
            public void run()
            {
                if(!continueOffline)
                {
                    if(isOnline())
                    {
                        isConnected = true;
                    }
                    else
                    {
                        isConnected = false;
                        if(!alertDialog.isShowing())
                        {
                            alertDialog.show();
                        }
                    }
                    handler.postDelayed(this,5000);
                }
            }
        };
        checkConstantly.run();
        // Should happen once at the start of the session
        if(isConnected)
        {
            progressDialog.setMessage("Loading....");
            progressDialog.show();

            // create handle for the RetrofitInstance interface
            GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            Call<List<NewsStory>> call = service.getAllNews();
            call.enqueue(new Callback<List<NewsStory>>()
            {
                @Override
                public void onResponse(Call<List<NewsStory>> call, Response<List<NewsStory>> response)
                {
                    progressDialog.dismiss();
                    generateDataList(response.body());
                }
                @Override
                public void onFailure(Call<List<NewsStory>> call, Throwable t)
                {
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    public void browseOnline(View view)
    {
        refreshActivity();
    }
    public boolean isOnline()
    {
        ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable())
        {
            return false;
        }
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        // Create an ArrayAdapter that will contain all list items
        ArrayAdapter<String> adapter;
        if(typesArray == null)
        {
            typesArray = getResources().getStringArray(R.array.types);
        }
        switch(item.getItemId())
        {
            case R.id.select_type:
                AlertDialog.Builder b = new AlertDialog.Builder(this);
                b.setTitle("Filter by:");
                b.setItems(typesArray, (dialog, which) ->
                {
                    dialog.dismiss();
                    switch(which)
                    {
                        case 0:
                            applyFilter("Contentpackage");
                            break;
                        case 1:
                            applyFilter("Story");
                            break;
                    }
                });
                b.show();
                break;
            case android.R.id.home:
                if(filterActivated)
                {
                    unApplyFilter();
                    break;
                }
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public void unApplyFilter()
    {
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle("CBC News");
        newsAdapter.updateRecyclerData(originalNews);
        filterActivated = false;
        invalidateOptionsMenu();
    }
    public void applyFilter(String filter)
    {
        filterActivated = true;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Filter: " + filter);
        originalNews = newsAdapter.getNewsStories();
        List<NewsStory> filteredNews = new ArrayList<>();
        for(NewsStory newsStory : newsAdapter.getNewsStories())
        {
            if(newsStory.getType().toLowerCase().equals(filter.toLowerCase()))
            {
                filteredNews.add(newsStory);
            }
        }
        if(filteredNews.size() > 0)
        {
            newsAdapter.updateRecyclerData(filteredNews);
        }
        else
        {
            Snackbar.make(coordinatorLayout, "No current news in that category", Snackbar.LENGTH_LONG).show();
            filterActivated = false;
            unApplyFilter();
        }
        invalidateOptionsMenu();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        Log.d(getClass().getSimpleName(), "filterActivated: " + filterActivated);
        if(filterActivated)
        {
            menu.findItem(R.id.select_type).setVisible(false);
        }
        else
        {
            menu.findItem(R.id.select_type).setVisible(true);
        }
        return true;
    }
    private void generateDataList(List<NewsStory> newsList)
    {
        recyclerView = findViewById(R.id.news_recyclerview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        // if its null, you know you're falling back on setting up from whatever is there in the database
        if(newsList != null && newsList.size() > 0)
        {
            newsStoryViewModel.insertAll(newsList);
        }
        newsStoryViewModel.getAllNews().observe(this, new Observer<List<NewsStory>>()
        {
            @Override
            public void onChanged(List<NewsStory> newsStories)
            {
                newsAdapter = new NewsAdapter(getApplicationContext(), newsStoryViewModel.getAllNews().getValue());
                recyclerView.setAdapter(newsAdapter);
            }
        });
    }
    public void refreshActivity()
    {
        // Kill it with fire
        handler.removeCallbacks(checkConstantly);
        handler.removeCallbacksAndMessages(null);
        handler = null;
        checkConstantly = null;

        // Start fresh
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}