package com.luxoft.carsapp.presentation.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.luxoft.carsapp.R;
import com.luxoft.carsapp.domain.di.HasComponent;
import com.luxoft.carsapp.domain.di.components.CarComponent;
import com.luxoft.carsapp.domain.di.components.DaggerCarComponent;
import com.luxoft.carsapp.domain.di.modules.CarModule;
import com.luxoft.carsapp.presentation.model.CarModel;
import com.luxoft.carsapp.presentation.presenter.CarDetailsPresenter;
import com.luxoft.carsapp.presentation.view.CarDetailsView;
import com.luxoft.carsapp.presentation.view.activity.BrowserActivity;
import com.luxoft.carsapp.presentation.view.activity.PhotoActivity;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.luxoft.carsapp.presentation.view.activity.BrowserActivity.URL_KEY;
import static com.luxoft.carsapp.presentation.view.activity.PhotoActivity.PHOTO_KEY;

public class CarDetailsFragment extends BaseFragment implements CarDetailsView, HasComponent<CarComponent> {
    private CarComponent carComponent;
    private String wikiUrl = "";
    private String photoUrl = "";
    public static String model;
    @Inject
    CarDetailsPresenter carDetailsPresenter;
    @Bind(R.id.imageViewCover)
    ImageView ivCover;
    @Bind(R.id.tv_model)
    TextView tvModel;
    @Bind(R.id.rl_progress)
    RelativeLayout rlProgress;
    @Bind(R.id.rl_retry)
    RelativeLayout rlRetry;
    @Bind(R.id.btn_retry)
    Button btnRetry;
    @Bind(R.id.buttonWiki)
    Button buttonWiki;

    public CarDetailsFragment() {
        setRetainInstance(true);
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
                .carModule(new CarModule(model))
                .build();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_car_details, container, false);
        ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.carDetailsPresenter.setView(this);
        if (savedInstanceState == null) {
            this.loadProductDetails();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        this.carDetailsPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.carDetailsPresenter.pause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.carDetailsPresenter.destroy();
    }

    public void renderCar(CarModel carModel) {
        if (carModel != null) {
            if (carModel.getImg() != null) {
                Picasso.with(getContext()).load(carModel.getImg()).into(ivCover);
            }
            this.tvModel.setText(carModel.getModel());
            wikiUrl = carModel.getWiki();
            photoUrl = carModel.getImg();
        }
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

    @Override
    public void showError(String message) {
        this.showToastMessage(message);
    }

    @Override
    public Context context() {
        return getActivity().getApplicationContext();
    }

    private void loadProductDetails() {
        if (this.carDetailsPresenter != null) {
            this.carDetailsPresenter.initialize();
        }
    }

    @OnClick(R.id.btn_retry)
    void onButtonRetryClick() {
        CarDetailsFragment.this.loadProductDetails();
    }

    @OnClick(R.id.buttonWiki)
    void onButtonWikiClick() {
        Intent intent = new Intent(context(), BrowserActivity.class);
        intent.putExtra(URL_KEY, wikiUrl);
        startActivity(intent);
    }

    @OnClick(R.id.imageViewCover)
    void onImageViewCoverClick() {
        Intent intent = new Intent(context(), PhotoActivity.class);
        intent.putExtra(PHOTO_KEY, photoUrl);
        startActivity(intent);
    }

    @Override
    public CarComponent getComponent() {
        return carComponent;
    }
}
