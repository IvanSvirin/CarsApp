package com.luxoft.carsapp.data.repository.datasource;

import android.content.Context;

import com.luxoft.carsapp.data.cache.CarCache;
import com.luxoft.carsapp.data.entity.CarEntity;
import com.luxoft.carsapp.data.exception.NetworkConnectionException;
import com.luxoft.carsapp.data.rest.ApiInterface;
import com.luxoft.carsapp.util.Utils;

import java.util.List;

import rx.Observable;

class CloudCarDataStore implements CarDataStore {
    private final Context context;
    private final ApiInterface apiInterface;
    private final CarCache carCache;

    public CloudCarDataStore(Context context, ApiInterface apiInterface, CarCache carCache) {
        this.context = context;
        this.apiInterface = apiInterface;
        this.carCache = carCache;
    }

    @Override
    public Observable<List<CarEntity>> carEntityList() {
        if (Utils.isThereInternetConnection(context)) {
            return this.apiInterface.carEntityList().concatMap(carResponse -> Observable.just(carResponse.getItems()))
                    .doOnNext(CloudCarDataStore.this.carCache::putCars);
        } else {
            return Observable.create(subscriber -> {
                subscriber.onError(new NetworkConnectionException());
            });
        }
    }

    @Override
    public Observable<List<CarEntity>> carEntityList(String manufacturer) {
        if (Utils.isThereInternetConnection(context)) {
            return this.apiInterface.carEntityList().concatMap(carResponse -> Observable.just(carResponse.getItems()))
                    .doOnNext(CloudCarDataStore.this.carCache::putCars)
                    .flatMap(Observable::from)
                    .filter(productEntity -> productEntity.getManufacturer().equals(manufacturer)).toList();
        } else {
            return Observable.create(subscriber -> {
                subscriber.onError(new NetworkConnectionException());
            });
        }
    }

    @Override
    public Observable<CarEntity> carEntityDetails(String model) {
        if (Utils.isThereInternetConnection(context)) {
            return this.apiInterface.productEntity(model).doOnNext(CloudCarDataStore.this.carCache::putCar);
        } else {
            return Observable.create(subscriber -> {
                subscriber.onError(new NetworkConnectionException());
            });
        }
    }
}
