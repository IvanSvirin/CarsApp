package com.luxoft.carsapp.data.rest;


import com.luxoft.carsapp.util.Constants;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Singleton
public class RetrofitClient {
    public Retrofit getRetrofit() {
        int connectTimeoutInSeconds = 30;
        int writeTimeoutInSeconds = 30;
        int readTimeoutInSeconds = 30;
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .connectTimeout(connectTimeoutInSeconds, TimeUnit.SECONDS)
                .readTimeout(readTimeoutInSeconds, TimeUnit.SECONDS)
                .writeTimeout(writeTimeoutInSeconds, TimeUnit.SECONDS);

        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.API_BASE_URL)
                .client(httpClient.build())
                .build();
    }
}
