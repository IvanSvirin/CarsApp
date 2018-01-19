package com.luxoft.carsapp.presentation.presenter;

import android.support.annotation.NonNull;


import com.luxoft.carsapp.domain.di.PerActivity;
import com.luxoft.carsapp.domain.exception.DefaultErrorBundle;
import com.luxoft.carsapp.domain.exception.ErrorBundle;
import com.luxoft.carsapp.domain.interactor.DefaultSubscriber;
import com.luxoft.carsapp.domain.interactor.UseCase;
import com.luxoft.carsapp.domain.model.Car;
import com.luxoft.carsapp.domain.model.mapper.ModelDataMapper;
import com.luxoft.carsapp.presentation.exception.ErrorMessageFactory;
import com.luxoft.carsapp.presentation.model.CarModel;
import com.luxoft.carsapp.presentation.view.CarListView;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

@PerActivity
public class CarListPresenter implements Presenter {

    private CarListView viewListView;

    private final UseCase getCarListUseCase;
    private final ModelDataMapper carModelDataMapper;

    @Inject
    public CarListPresenter(@Named("carList") UseCase getCarListUseCase, ModelDataMapper carModelDataMapper) {
        this.getCarListUseCase = getCarListUseCase;
        this.carModelDataMapper = carModelDataMapper;
    }

    public void setView(@NonNull CarListView view) {
        this.viewListView = view;
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void destroy() {
        this.getCarListUseCase.unsubscribe();
        this.viewListView = null;
    }

    public void initialize() {
        this.loadCarList();
    }

    private void loadCarList() {
        this.hideViewRetry();
        this.showViewLoading();
        this.getCarList();
    }

    public void onCarClicked(CarModel carModel) {
        this.viewListView.viewCar(carModel);
    }

    private void showViewLoading() {
        this.viewListView.showLoading();
    }

    private void hideViewLoading() {
        this.viewListView.hideLoading();
    }

    private void showViewRetry() {
        this.viewListView.showRetry();
    }

    private void hideViewRetry() {
        this.viewListView.hideRetry();
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.viewListView.context(),
                errorBundle.getException());
        this.viewListView.showError(errorMessage);
    }

    private void showCarsCollectionInView(Collection<Car> carCollection) {
        final Collection<CarModel> carModelCollection =
                this.carModelDataMapper.transformCars(carCollection);
        this.viewListView.renderCarList(carModelCollection);
    }

    private void getCarList() {
        this.getCarListUseCase.execute(new CarListSubscriber());
    }

    private final class CarListSubscriber extends DefaultSubscriber<List<Car>> {

        @Override
        public void onCompleted() {
            CarListPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            CarListPresenter.this.hideViewLoading();
            CarListPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            CarListPresenter.this.showViewRetry();
        }

        @Override
        public void onNext(List<Car> cars) {
            CarListPresenter.this.showCarsCollectionInView(cars);
        }
    }
}
