package com.luxoft.carsapp.presentation.view.fragment;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.luxoft.carsapp.CarApplication;
import com.luxoft.carsapp.domain.di.HasComponent;
import com.luxoft.carsapp.domain.di.components.ApplicationComponent;
import com.luxoft.carsapp.domain.di.modules.ActivityModule;

public abstract class BaseFragment extends Fragment {
    protected void showToastMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @SuppressWarnings("unchecked")
    protected <C> C getComponent(Class<C> componentType) {
        return componentType.cast(((HasComponent<C>) this).getComponent());
//        return componentType.cast(((HasComponent<C>) getActivity()).getComponent());
    }

    protected ApplicationComponent getApplicationComponent() {
        return ((CarApplication) getActivity().getApplication()).getApplicationComponent();
    }

    protected ActivityModule getActivityModule(AppCompatActivity appCompatActivity) {
        return new ActivityModule(appCompatActivity);
    }

}
