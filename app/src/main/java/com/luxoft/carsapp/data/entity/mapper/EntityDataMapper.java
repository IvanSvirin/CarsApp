package com.luxoft.carsapp.data.entity.mapper;
import com.luxoft.carsapp.data.entity.CarEntity;
import com.luxoft.carsapp.domain.model.Car;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class EntityDataMapper {
  @Inject
  public EntityDataMapper() {}

  public Car transform(CarEntity carEntity) {
    Car car = null;
    if (carEntity != null) {
      car = new Car(carEntity.getModel());
      car.setManufacturer(carEntity.getManufacturer());
      car.setModel(carEntity.getModel());
      car.setPrice(carEntity.getPrice());
      car.setWiki(carEntity.getWiki());
      car.setImg(carEntity.getImg());
    }
    return car;
  }


  public List<Car> transformCars(Collection<CarEntity> carEntityCollection) {
    List<Car> cars = new ArrayList<>(20);
    Car car;
    for (CarEntity carEntity : carEntityCollection) {
      car = transform(carEntity);
      if (car != null) {
        cars.add(car);
      }
    }
    return cars;
  }
}
