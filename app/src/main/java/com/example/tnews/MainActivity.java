package com.example.tnews;


import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TP_Adapter newsAdapter;
    private String selectedCategory = "Everything";
    private String apiKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiKey = getString(R.string.api_key);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Spinner categorySpinner = findViewById(R.id.categorySpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {
                selectedCategory = parent.getItemAtPosition(position).toString();
                fetchNewsByCategory(); // Charger les nouvelles pour la catégorie sélectionnée
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

    }



    private void fetchNewsByCategory() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://newsapi.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        News_Api newsApi = retrofit.create(News_Api.class);
        //String apiKey = "00d47b2fd80a4937ba8d040949ba8ccc";

        Call<TP_Response> call = newsApi.getNews(selectedCategory.toLowerCase(), apiKey);
        //Toast.makeText(MainActivity.this, "categories selected  : "+selectedCategory.toLowerCase(),Toast.LENGTH_SHORT).show();
        call.enqueue(new Callback<TP_Response>() {
            @Override
            public void onResponse(Call<TP_Response> call, Response<TP_Response> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<TP_Article> articles = response.body().getArticles();

                    newsAdapter = new TP_Adapter(MainActivity.this, articles);
                    recyclerView.setAdapter(newsAdapter);
                } else {
                    Log.e("MainActivity", "Response unsuccessful: Code " + response.code());
                }
            }

            @Override
            public void onFailure(Call<TP_Response> call, Throwable t) {
                Log.e("MainActivity", "Error fetching news: " + t.getMessage());
                Toast.makeText(MainActivity.this, "Error fetching news:  " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

