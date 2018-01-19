package com.luxoft.carsapp.domain.repository;

import com.luxoft.carsapp.domain.model.Car;

import java.util.List;

import rx.Observable;

public interface CarRepository {

  Observable<List<Car>> cars();

  Observable<List<Car>> cars(final String manufacturer);

  Observable<Car> car(final String model);
}
