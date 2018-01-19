package com.luxoft.carsapp.domain.model.mapper;

import com.luxoft.carsapp.domain.di.PerActivity;
import com.luxoft.carsapp.domain.model.Car;
import com.luxoft.carsapp.presentation.model.CarModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.inject.Inject;

@PerActivity
public class ModelDataMapper {

  @Inject
  public ModelDataMapper() {}

  public CarModel transform(Car car) {
    if (car == null) {
      throw new IllegalArgumentException("Cannot transform a null value");
    }
    CarModel carModel = new CarModel(car.getModel());
    carModel.setModel(car.getModel());
    carModel.setManufacturer(car.getManufacturer());
    carModel.setPrice(car.getPrice());
    carModel.setImg(car.getImg());
    carModel.setWiki(car.getWiki());
    return carModel;
  }

  public Collection<CarModel> transformCars(Collection<Car> carCollection) {
    Collection<CarModel> carModelCollection;

    if (carCollection != null && !carCollection.isEmpty()) {
      carModelCollection = new ArrayList<>();
      for (Car car : carCollection) {
        carModelCollection.add(transform(car));
      }
    } else {
      carModelCollection = Collections.emptyList();
    }
    return carModelCollection;
  }
}
