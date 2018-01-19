package com.luxoft.carsapp.domain.di.modules;

import android.content.Context;

import com.luxoft.carsapp.data.cache.DaoClient;
import com.luxoft.carsapp.data.entity.DaoSession;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DaoModule {

    @Provides
    @Singleton
    DaoClient provideDaoClient(Context context) {
        return new DaoClient(context);
    }

    @Provides
    @Singleton
    DaoSession provideDaoSession(DaoClient daoClient) {
        return daoClient.getDaoSession();
    }

}
