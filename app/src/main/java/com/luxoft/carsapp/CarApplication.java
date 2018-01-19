package com.luxoft.carsapp;

import android.app.Application;
import android.support.compat.BuildConfig;

import com.luxoft.carsapp.domain.di.components.ApplicationComponent;
import com.luxoft.carsapp.domain.di.components.DaggerApplicationComponent;
import com.luxoft.carsapp.domain.di.modules.ApplicationModule;

public class CarApplication extends Application {
    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        this.initializeInjector();
        this.initializeLeakDetection();
    }

    private void initializeInjector() {
        this.applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return this.applicationComponent;
    }

    private void initializeLeakDetection() {
        if (BuildConfig.DEBUG) {
//      LeakCanary.install(this);
        }
    }
}
