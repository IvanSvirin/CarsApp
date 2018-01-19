package com.luxoft.carsapp.domain.di.modules;

import android.content.Context;

import com.luxoft.carsapp.CarApplication;
import com.luxoft.carsapp.data.cache.CarCache;
import com.luxoft.carsapp.data.cache.CarCacheImpl;
import com.luxoft.carsapp.data.executor.JobExecutor;
import com.luxoft.carsapp.data.repository.CarDataRepository;
import com.luxoft.carsapp.domain.executor.PostExecutionThread;
import com.luxoft.carsapp.domain.executor.ThreadExecutor;
import com.luxoft.carsapp.domain.repository.CarRepository;
import com.luxoft.carsapp.presentation.UIThread;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
  private final CarApplication application;

  public ApplicationModule(CarApplication application) {
    this.application = application;
  }

  @Provides
  @Singleton
  Context provideApplicationContext() {
    return this.application;
  }

  @Provides
  @Singleton
  ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
    return jobExecutor;
  }

  @Provides
  @Singleton
  PostExecutionThread providePostExecutionThread(UIThread uiThread) {
    return uiThread;
  }

  @Provides
  @Singleton
  CarCache provideCarCache(CarCacheImpl productCache) {
    return productCache;
  }

  @Provides
  @Singleton
  CarRepository provideCarRepository(CarDataRepository carDataRepository) {
    return carDataRepository;
  }
}
