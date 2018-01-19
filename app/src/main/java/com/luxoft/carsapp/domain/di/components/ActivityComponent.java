package com.luxoft.carsapp.domain.di.components;

import android.support.v7.app.AppCompatActivity;

import com.luxoft.carsapp.domain.di.PerActivity;
import com.luxoft.carsapp.domain.di.modules.ActivityModule;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
  AppCompatActivity activity();
}
