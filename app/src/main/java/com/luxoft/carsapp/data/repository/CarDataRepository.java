package com.luxoft.carsapp.data.repository;


import com.luxoft.carsapp.data.entity.mapper.EntityDataMapper;
import com.luxoft.carsapp.data.repository.datasource.CarDataStore;
import com.luxoft.carsapp.data.repository.datasource.CarDataStoreFactory;
import com.luxoft.carsapp.domain.model.Car;
import com.luxoft.carsapp.domain.repository.CarRepository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

@Singleton
public class CarDataRepository implements CarRepository {

  private final CarDataStoreFactory carDataStoreFactory;
  private final EntityDataMapper entityDataMapper;

  @Inject
  public CarDataRepository(CarDataStoreFactory dataStoreFactory, EntityDataMapper entityDataMapper) {
    this.carDataStoreFactory = dataStoreFactory;
    this.entityDataMapper = entityDataMapper;
  }

  @Override
  public Observable<List<Car>> cars() {
    final CarDataStore carDataStore = this.carDataStoreFactory.createList();
    return carDataStore.carEntityList().map(this.entityDataMapper::transformCars);
  }

  @Override
  public Observable<List<Car>> cars(String manufacturer) {
    final CarDataStore productDataStore = this.carDataStoreFactory.create(manufacturer);
    return productDataStore.carEntityList(manufacturer).map(this.entityDataMapper::transformCars);
  }

  @Override
  public Observable<Car> car(String model) {
    final CarDataStore productDataStore = this.carDataStoreFactory.create(model);
    return productDataStore.carEntityDetails(model).map(this.entityDataMapper::transform);
  }
}
