package com.luxoft.carsapp.domain.di.modules;

import com.luxoft.carsapp.domain.di.PerActivity;
import com.luxoft.carsapp.domain.executor.PostExecutionThread;
import com.luxoft.carsapp.domain.executor.ThreadExecutor;
import com.luxoft.carsapp.domain.interactor.GetCarByManufacturerList;
import com.luxoft.carsapp.domain.interactor.GetCarDetails;
import com.luxoft.carsapp.domain.interactor.GetCarList;
import com.luxoft.carsapp.domain.interactor.UseCase;
import com.luxoft.carsapp.domain.repository.CarRepository;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class CarModule {
    private String model = "";
    private String manufacturer = "";

    public CarModule() {
    }

    public CarModule(String model) {
        this.model = model;
    }

    public CarModule(String model, String manufacturer) {
        this.model = model;
        this.manufacturer = manufacturer;
    }

    @Provides
    @PerActivity
    @Named("carList")
    UseCase provideGetCarListUseCase(GetCarList getCarList) {
        return getCarList;
    }

    @Provides
    @PerActivity
    @Named("carByManufacturerList")
    UseCase provideGetCarByManufacturerListUseCase(CarRepository carRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        return new GetCarByManufacturerList(carRepository, threadExecutor, postExecutionThread, manufacturer);
    }

    @Provides
    @PerActivity
    @Named("carDetails")
    UseCase provideGetCarDetailsUseCase(CarRepository carRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        return new GetCarDetails(model, carRepository, threadExecutor, postExecutionThread);
    }
}