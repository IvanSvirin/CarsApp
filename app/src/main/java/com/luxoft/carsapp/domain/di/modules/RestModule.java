package com.luxoft.carsapp.domain.di.modules;

import com.luxoft.carsapp.data.rest.ApiInterface;
import com.luxoft.carsapp.data.rest.RetrofitClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RestModule {
    @Provides
    @Singleton
    RetrofitClient provideRetrofitClient() {
        return new RetrofitClient();
    }

    @Provides
    @Singleton
    ApiInterface provideApiInterface(RetrofitClient retrofitClient) {
        return retrofitClient.getRetrofit().create(ApiInterface.class);
    }
}
