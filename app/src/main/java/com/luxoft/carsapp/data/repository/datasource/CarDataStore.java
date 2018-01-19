package com.luxoft.carsapp.data.repository.datasource;


import com.luxoft.carsapp.data.entity.CarEntity;

import java.util.List;

import rx.Observable;

public interface CarDataStore {

  Observable<List<CarEntity>> carEntityList();

  Observable<List<CarEntity>> carEntityList(final String manufacturer);

  Observable<CarEntity> carEntityDetails(final String model);

}
