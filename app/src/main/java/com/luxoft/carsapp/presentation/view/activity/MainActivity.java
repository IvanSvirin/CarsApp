package com.luxoft.carsapp.presentation.view.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.luxoft.carsapp.R;
import com.luxoft.carsapp.presentation.model.CarModel;
import com.luxoft.carsapp.presentation.view.fragment.CarDetailsFragment;
import com.luxoft.carsapp.presentation.view.fragment.CarListFragment;

public class MainActivity extends BaseActivity implements CarListFragment.CarListListener {
public static final String LIST_FRAGMENT = "list_fragment";
public static final String DETAILS_FRAGMENT = "details_fragment";
private String currentFragment = LIST_FRAGMENT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    protected void onResume() {
        super.onResume();
        switch (currentFragment) {
            case LIST_FRAGMENT:
                replaceFragment(R.id.container, new CarListFragment());
                currentFragment = LIST_FRAGMENT;
                break;
            case DETAILS_FRAGMENT:
                replaceFragment(R.id.container, new CarDetailsFragment());
                currentFragment = DETAILS_FRAGMENT;
                break;
        }
    }

    @Override
    public void onBackPressed() {
        switch (currentFragment) {
            case LIST_FRAGMENT:
                super.onBackPressed();
                break;
            case DETAILS_FRAGMENT:
                replaceFragment(R.id.container, new CarListFragment());
                currentFragment = LIST_FRAGMENT;
                break;
        }

    }

    @Override
    public void onCarClicked(CarModel productModel) {
        CarDetailsFragment.model = productModel.getModel();
        replaceFragment(R.id.container, new CarDetailsFragment());
        currentFragment = DETAILS_FRAGMENT;
    }
}
