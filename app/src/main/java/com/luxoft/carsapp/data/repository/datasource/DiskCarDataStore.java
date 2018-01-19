package com.luxoft.carsapp.data.repository.datasource;

import com.luxoft.carsapp.data.cache.CarCache;
import com.luxoft.carsapp.data.entity.CarEntity;

import java.util.List;

import rx.Observable;

class DiskCarDataStore implements CarDataStore {
  private final CarCache carCache;

  DiskCarDataStore(CarCache carCache) {
    this.carCache = carCache;
  }

  @Override
  public Observable<List<CarEntity>> carEntityList() {
    return this.carCache.getCars();
  }

  @Override
  public Observable<List<CarEntity>> carEntityList(String manufacturer) {
    return this.carCache.getCars(manufacturer);
  }

  @Override
  public Observable<CarEntity> carEntityDetails(String model) {
    return this.carCache.get(model);
  }
}
