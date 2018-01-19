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
public class CarByManufacturerListPresenter implements Presenter {
    private CarListView viewListView;
    private final UseCase getCarByManufacturerListUseCase;
    private final ModelDataMapper carModelDataMapper;

    @Inject
    public CarByManufacturerListPresenter(@Named("carByManufacturerList") UseCase getCarByManufacturerListUseCase, ModelDataMapper carModelDataMapper) {
        this.getCarByManufacturerListUseCase = getCarByManufacturerListUseCase;
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
        this.getCarByManufacturerListUseCase.unsubscribe();
        this.viewListView = null;
    }

    public void initialize() {
        this.loadProductList();
    }

    private void loadProductList() {
        this.hideViewRetry();
        this.showViewLoading();
        this.getProductList();
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

    private void showProductsCollectionInView(Collection<Car> productCollection) {
        final Collection<CarModel> productModelCollection =
                this.carModelDataMapper.transformCars(productCollection);
        this.viewListView.renderCarList(productModelCollection);
    }

    private void getProductList() {
        this.getCarByManufacturerListUseCase.execute(new CarListSubscriber());
    }

    private final class CarListSubscriber extends DefaultSubscriber<List<Car>> {

        @Override
        public void onCompleted() {
            CarByManufacturerListPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            CarByManufacturerListPresenter.this.hideViewLoading();
            CarByManufacturerListPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            CarByManufacturerListPresenter.this.showViewRetry();
        }

        @Override
        public void onNext(List<Car> products) {
            CarByManufacturerListPresenter.this.showProductsCollectionInView(products);
        }
    }
}
