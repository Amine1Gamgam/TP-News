package com.example.tnews;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface News_Api {
    @GET("v2/everything")
    Call<TP_Response> getNews(
            @Query("q") String query,
            @Query("apiKey") String apiKey
    );
}
