package com.luxoft.carsapp.domain.interactor;

import com.luxoft.carsapp.domain.executor.PostExecutionThread;
import com.luxoft.carsapp.domain.executor.ThreadExecutor;
import com.luxoft.carsapp.domain.repository.CarRepository;

import javax.inject.Inject;

import rx.Observable;

public class GetCarList extends UseCase {

  private final CarRepository carRepository;

  @Inject
  public GetCarList(CarRepository carRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    this.carRepository = carRepository;
  }

  @Override
  public Observable buildUseCaseObservable() {
    return this.carRepository.cars();
  }
}
