package com.example.goodsment.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterface {


    @GET("directions/json")
    Call<ModelRoutes> getResponseDistance(
            @Query("origin") String str,
            @Query("destination") String str1,
            @Query("key") String str2);
}