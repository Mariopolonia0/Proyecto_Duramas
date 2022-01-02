package com.example.almacen.conexion_api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiUtils {
    private ApiUtils() {}

    public static final String BASE_URL = "http://www.apiduramas.somee.com/api/";

    public static MaterialApi getMaterialApi() {

        return  RetrofitClient.getClient(BASE_URL).create(MaterialApi.class);
//
    }
}
