package com.luxoft.carsapp.presentation.view;


import com.luxoft.carsapp.presentation.model.CarModel;

public interface CarDetailsView extends LoadDataView {
  void renderCar(CarModel carModel);
}
