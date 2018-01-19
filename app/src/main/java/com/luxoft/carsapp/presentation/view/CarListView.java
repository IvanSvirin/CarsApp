package com.luxoft.carsapp.presentation.view;

import com.luxoft.carsapp.presentation.model.CarModel;

import java.util.Collection;

public interface CarListView extends LoadDataView {
    void renderCarList(Collection<CarModel> carModelCollection);

    void viewCar(CarModel carModel);
}
