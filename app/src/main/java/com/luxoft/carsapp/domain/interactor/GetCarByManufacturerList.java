package com.luxoft.carsapp.domain.interactor;
import com.luxoft.carsapp.domain.executor.PostExecutionThread;
import com.luxoft.carsapp.domain.executor.ThreadExecutor;
import com.luxoft.carsapp.domain.repository.CarRepository;

import javax.inject.Inject;

import rx.Observable;

public class GetCarByManufacturerList extends UseCase {
  private final String manufacturer;
  private final CarRepository carRepository;

  @Inject
  public GetCarByManufacturerList(CarRepository carRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, String categoryName) {
    super(threadExecutor, postExecutionThread);
    this.carRepository = carRepository;
    this.manufacturer = categoryName;
  }

  @Override
  public Observable buildUseCaseObservable() {
    return this.carRepository.cars(manufacturer);
  }
}
