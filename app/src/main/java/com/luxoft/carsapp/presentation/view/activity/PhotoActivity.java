package com.luxoft.carsapp.presentation.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.luxoft.carsapp.R;
import com.squareup.picasso.Picasso;

import butterknife.Bind;

public class PhotoActivity extends BaseActivity {
    public static final String PHOTO_KEY = "photo_key";
    private String photoUrl = "";
//    @Bind(R.id.imageViewPhoto)
    ImageView imageViewPhoto;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        imageViewPhoto = findViewById(R.id.imageViewPhoto);

        photoUrl = getIntent().getStringExtra(PHOTO_KEY);
        if (photoUrl != null) {
            Picasso.with(this).load(photoUrl).into(imageViewPhoto);
        }

    }
}
