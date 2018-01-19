package com.luxoft.carsapp.data.cache;


import com.luxoft.carsapp.data.entity.CarEntity;

import java.util.List;

import rx.Observable;

public interface CarCache {
    Observable<CarEntity> get(final String model);

    boolean isCached(final String model);

    boolean isExpired();

    void evictAll();



    Observable<List<CarEntity>> getCars();

    Observable<List<CarEntity>> getCars(final String manufacturer);

    void putCar(CarEntity carEntity);

    void putCars(List<CarEntity> carEntities);

    boolean isCarsCached();
}
