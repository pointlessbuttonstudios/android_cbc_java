package com.example.android_cbc_java;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android_cbc_java.newsstory.NewsStory;
import com.example.android_cbc_java.network.RetrofitClientInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
{
    private NewsAdapter news;
    private RecyclerView recyclerView;
    private ProgressDialog progressDialog;
    private NewsStoryViewModel newsStoryViewModel;
    private String [] typesArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("CBC NEWS");
        setSupportActionBar(toolbar);

         newsStoryViewModel = ViewModelProviders.of(this).get(NewsStoryViewModel.class);
        // Create Utility Singleton
        Utility.init(this, newsStoryViewModel);

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        // create handle for the RetrofitInstance interface
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<List<NewsStory>> call = service.getAllNews();
        call.enqueue(new Callback<List<NewsStory>>() {
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
                b.setItems(typesArray, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                        switch(which){
                            case 0:
                                break;
                            case 1:
                                break;
                        }
                    }

                });
                b.show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    private void generateDataList(List<NewsStory> newsList)
    {
        newsStoryViewModel.getAllNews().observe(this, new Observer<List<NewsStory>>()
        {
            @Override
            public void onChanged(List<NewsStory> newsStories)
            {
            }
        });
//        for(NewsStory ns : newsList)
//        {
//            Log.d(this.getLocalClassName(), ns.toString());
//        }
        recyclerView = findViewById(R.id.news_recyclerview);
        news = new NewsAdapter(this, newsList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(news);
    }
}