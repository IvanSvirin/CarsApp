package com.luxoft.carsapp.domain.interactor;

import com.luxoft.carsapp.domain.executor.PostExecutionThread;
import com.luxoft.carsapp.domain.executor.ThreadExecutor;
import com.luxoft.carsapp.domain.repository.CarRepository;

import javax.inject.Inject;

import rx.Observable;

public class GetCarDetails extends UseCase {
    private final String model;
    private final CarRepository carRepository;

    @Inject
    public GetCarDetails(String model, CarRepository carRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.model = model;
        this.carRepository = carRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return this.carRepository.car(this.model);
    }
}
