package com.luxoft.carsapp.domain.di.components;


import com.luxoft.carsapp.domain.di.PerActivity;
import com.luxoft.carsapp.domain.di.modules.ActivityModule;
import com.luxoft.carsapp.domain.di.modules.CarModule;
import com.luxoft.carsapp.presentation.view.fragment.CarByManufacturerListFragment;
import com.luxoft.carsapp.presentation.view.fragment.CarDetailsFragment;
import com.luxoft.carsapp.presentation.view.fragment.CarListFragment;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, CarModule.class})
public interface CarComponent extends ActivityComponent {
  void inject(CarListFragment carListFragment);
  void inject(CarByManufacturerListFragment carByManufacturerListFragment);
  void inject(CarDetailsFragment carDetailsFragment);
}
