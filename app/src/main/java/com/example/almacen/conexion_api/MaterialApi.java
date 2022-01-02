package com.example.almacen.conexion_api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface MaterialApi {
    @GET("Materials")
    Call<List<Materials>> getMateriales();

    @POST("Materials")
   // @FormUrlEncoded
    Call<Materials> saveMateriales(@Body Materials materials);
}

