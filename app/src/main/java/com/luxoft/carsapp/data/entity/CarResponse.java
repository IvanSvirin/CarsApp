package com.luxoft.carsapp.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CarResponse {
    @SerializedName("data")
    private List<CarEntity> items;

    public CarResponse(List<CarEntity> items) {
        this.items = items;
    }

    public List<CarEntity> getItems() {
        return items;
    }

    public void setItems(List<CarEntity> items) {
        this.items = items;
    }
}
