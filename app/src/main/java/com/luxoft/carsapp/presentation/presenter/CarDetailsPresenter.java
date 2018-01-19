package com.luxoft.carsapp.presentation.presenter;

import android.support.annotation.NonNull;

import com.fernandocejas.frodo.annotation.RxLogSubscriber;
import com.luxoft.carsapp.domain.di.PerActivity;
import com.luxoft.carsapp.domain.exception.DefaultErrorBundle;
import com.luxoft.carsapp.domain.exception.ErrorBundle;
import com.luxoft.carsapp.domain.interactor.DefaultSubscriber;
import com.luxoft.carsapp.domain.interactor.UseCase;
import com.luxoft.carsapp.domain.model.Car;
import com.luxoft.carsapp.domain.model.mapper.ModelDataMapper;
import com.luxoft.carsapp.presentation.exception.ErrorMessageFactory;
import com.luxoft.carsapp.presentation.model.CarModel;
import com.luxoft.carsapp.presentation.view.CarDetailsView;

import javax.inject.Inject;
import javax.inject.Named;

@PerActivity
public class CarDetailsPresenter implements Presenter {

  private CarDetailsView viewDetailsView;

  private final UseCase getCarDetailsUseCase;
  private final ModelDataMapper carModelDataMapper;

  @Inject
  public CarDetailsPresenter(@Named("carDetails") UseCase getCarDetailsUseCase, ModelDataMapper carModelDataMapper) {
    this.getCarDetailsUseCase = getCarDetailsUseCase;
    this.carModelDataMapper = carModelDataMapper;
  }

  public void setView(@NonNull CarDetailsView view) {
    this.viewDetailsView = view;
  }

  @Override
  public void resume() {}

  @Override
  public void pause() {}

  @Override
  public void destroy() {
    this.getCarDetailsUseCase.unsubscribe();
    this.viewDetailsView = null;
  }

  public void initialize() {
    this.loadCarDetails();
  }

  private void loadCarDetails() {
    this.hideViewRetry();
    this.showViewLoading();
    this.getCarDetails();
  }

  private void showViewLoading() {
    this.viewDetailsView.showLoading();
  }

  private void hideViewLoading() {
    this.viewDetailsView.hideLoading();
  }

  private void showViewRetry() {
    this.viewDetailsView.showRetry();
  }

  private void hideViewRetry() {
    this.viewDetailsView.hideRetry();
  }

  private void showErrorMessage(ErrorBundle errorBundle) {
    String errorMessage = ErrorMessageFactory.create(this.viewDetailsView.context(), errorBundle.getException());
    this.viewDetailsView.showError(errorMessage);
  }

  private void showCarDetailsInView(Car car) {
    final CarModel carModel = this.carModelDataMapper.transform(car);
    this.viewDetailsView.renderCar(carModel);
  }

  private void getCarDetails() {
    this.getCarDetailsUseCase.execute(new CarDetailsSubscriber());
  }

  @RxLogSubscriber
  private final class CarDetailsSubscriber extends DefaultSubscriber<Car> {

    @Override
    public void onCompleted() {
      CarDetailsPresenter.this.hideViewLoading();
    }

    @Override
    public void onError(Throwable e) {
      CarDetailsPresenter.this.hideViewLoading();
      CarDetailsPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
      CarDetailsPresenter.this.showViewRetry();
    }

    @Override
    public void onNext(Car car) {
      CarDetailsPresenter.this.showCarDetailsInView(car);
    }
  }
}
