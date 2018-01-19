package com.luxoft.carsapp.domain.di.components;

import android.content.Context;

import com.luxoft.carsapp.domain.di.modules.ApplicationModule;
import com.luxoft.carsapp.domain.di.modules.DaoModule;
import com.luxoft.carsapp.domain.di.modules.RestModule;
import com.luxoft.carsapp.domain.executor.PostExecutionThread;
import com.luxoft.carsapp.domain.executor.ThreadExecutor;
import com.luxoft.carsapp.domain.repository.CarRepository;
import com.luxoft.carsapp.presentation.view.activity.BaseActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, RestModule.class, DaoModule.class})
public interface ApplicationComponent {
  void inject(BaseActivity baseActivity);

  Context context();
  ThreadExecutor threadExecutor();
  PostExecutionThread postExecutionThread();
  CarRepository carRepository();
}
