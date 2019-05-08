package com.esgi.project.underdico.utils;

import android.content.Context;

import com.esgi.project.underdico.R;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiInstance {

    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstance(Context context) {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(context.getString(R.string.API_URL))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
