package com.example.user.teamvoytest.model.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiCategoryModule {

    public static ApiCategoryInterface getApiInterface() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.unsplash.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiCategoryInterface apiCategoryInterface = retrofit.create(ApiCategoryInterface.class);

        return apiCategoryInterface;
    }
}
