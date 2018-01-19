package com.luxoft.carsapp.data.repository.datasource;

import android.content.Context;
import android.support.annotation.NonNull;

import com.luxoft.carsapp.data.cache.CarCache;
import com.luxoft.carsapp.data.rest.ApiInterface;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CarDataStoreFactory {
  private final Context context;
  private final CarCache carCache;
  private final ApiInterface apiInterface;

  @Inject
  public CarDataStoreFactory(@NonNull Context context, @NonNull CarCache carCache, ApiInterface apiInterface) {
    this.context = context.getApplicationContext();
    this.carCache = carCache;
    this.apiInterface = apiInterface;
  }

  public CarDataStore create(String model) {
    CarDataStore productDataStore;
//    if (!this.carCache.isExpired() && this.carCache.isCached(model)) {
      productDataStore = new DiskCarDataStore(this.carCache);
//    } else {
//      productDataStore = createCloudDataStore();
//    }
    return productDataStore;
  }

  public CarDataStore createList() {
    CarDataStore productDataStore;
    if (!this.carCache.isExpired() && this.carCache.isCarsCached()) {
      productDataStore = new DiskCarDataStore(this.carCache);
    } else {
      productDataStore = createCloudDataStore();
    }
    return productDataStore;
  }

  public CarDataStore createCloudDataStore() {
    return new CloudCarDataStore(context, apiInterface, carCache);
  }
}
