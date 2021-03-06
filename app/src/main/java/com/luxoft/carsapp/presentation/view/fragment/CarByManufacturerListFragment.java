package com.luxoft.carsapp.presentation.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.luxoft.carsapp.R;
import com.luxoft.carsapp.domain.di.HasComponent;
import com.luxoft.carsapp.domain.di.components.CarComponent;
import com.luxoft.carsapp.domain.di.components.DaggerCarComponent;
import com.luxoft.carsapp.domain.di.modules.CarModule;
import com.luxoft.carsapp.presentation.model.CarModel;
import com.luxoft.carsapp.presentation.presenter.CarByManufacturerListPresenter;
import com.luxoft.carsapp.presentation.view.CarListView;
import com.luxoft.carsapp.presentation.view.adapter.CarsAdapter;
import com.luxoft.carsapp.util.CommonLayoutManager;

import java.util.Collection;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CarByManufacturerListFragment extends BaseFragment implements CarListView, HasComponent<CarComponent> {

    public interface CarListListener {
        void onProductClicked(final CarModel carModel);
    }
    public static String manufacturer;
    private CarComponent carComponent;
    @Inject
    CarByManufacturerListPresenter carByManufacturerListPresenter;
    @Inject
    CarsAdapter carsAdapter;
    @Bind(R.id.rv_cars)
    RecyclerView rvCars;
    @Bind(R.id.rl_progress)
    RelativeLayout rlProgress;
    @Bind(R.id.rl_retry)
    RelativeLayout rlRetry;
    @Bind(R.id.btn_retry)
    Button btnRetry;
    private CarListListener carListListener;

    public CarByManufacturerListFragment() {
        setRetainInstance(true);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof CarListListener) {
            this.carListListener = (CarListListener) activity;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.initializeInjector();
        this.getComponent(CarComponent.class).inject(this);
    }

    private void initializeInjector() {
        this.carComponent = DaggerCarComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule((AppCompatActivity) getActivity()))
                .carModule(new CarModule("", manufacturer))
                .build();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_car_list, container, false);
        ButterKnife.bind(this, fragmentView);
        setupRecyclerView();
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.carByManufacturerListPresenter.setView(this);
        if (savedInstanceState == null) {
            this.loadProductList();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        this.carByManufacturerListPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.carByManufacturerListPresenter.pause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        rvCars.setAdapter(null);
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.carByManufacturerListPresenter.destroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.carListListener = null;
    }

    @Override
    public void showLoading() {
        this.rlProgress.setVisibility(View.VISIBLE);
        this.getActivity().setProgressBarIndeterminateVisibility(true);
    }

    @Override
    public void hideLoading() {
        this.rlProgress.setVisibility(View.GONE);
        this.getActivity().setProgressBarIndeterminateVisibility(false);
    }

    @Override
    public void showRetry() {
        this.rlRetry.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRetry() {
        this.rlRetry.setVisibility(View.GONE);
    }

    public void renderCarList(Collection<CarModel> carModelCollection) {
        if (carModelCollection != null) {
            this.carsAdapter.setCarsCollection(carModelCollection);
        }
    }

    public void viewCar(CarModel carModel) {
        if (this.carListListener != null) {
            this.carListListener.onProductClicked(carModel);
        }
    }

    @Override
    public void showError(String message) {
        this.showToastMessage(message);
    }

    @Override
    public Context context() {
        return this.getActivity().getApplicationContext();
    }

    private void setupRecyclerView() {
        this.carsAdapter.setOnItemClickListener(onItemClickListener);
        this.rvCars.setLayoutManager(new GridLayoutManager(context(), 2));
        this.rvCars.setAdapter(carsAdapter);
    }

    private void loadProductList() {
        this.carByManufacturerListPresenter.initialize();
    }

    @OnClick(R.id.btn_retry)
    void onButtonRetryClick() {
        CarByManufacturerListFragment.this.loadProductList();
    }

    private CarsAdapter.OnItemClickListener onItemClickListener =
            carModel -> {
                if (CarByManufacturerListFragment.this.carByManufacturerListPresenter != null && carModel != null) {
                    CarByManufacturerListFragment.this.carByManufacturerListPresenter.onCarClicked(carModel);
                }
            };

    @Override
    public CarComponent getComponent() {
        return carComponent;
    }
}
