package com.luxoft.carsapp.data.rest;

import com.luxoft.carsapp.data.entity.CarEntity;
import com.luxoft.carsapp.data.entity.CarResponse;

import java.util.List;

import javax.inject.Singleton;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

@Singleton
public interface ApiInterface {

    @GET("cars.json")
    Observable<CarResponse> carEntityList();

    @GET("cars.json/{model}")
    Observable<CarEntity> productEntity(@Path("model") String model);

}
