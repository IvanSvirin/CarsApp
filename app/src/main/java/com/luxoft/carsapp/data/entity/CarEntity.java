package com.luxoft.carsapp.data.entity;

import com.google.gson.annotations.SerializedName;
import com.luxoft.carsapp.data.entity.daoconverter.CarType;
import com.luxoft.carsapp.data.entity.daoconverter.CarTypeConverter;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;

@Entity(indexes = {
        @Index(value = "model ASC", unique = true)
})
public class CarEntity {
  @Id
  private Long id;
  @NotNull
  @SerializedName("manufacturer")
  private String manufacturer;
  @NotNull
  @SerializedName("model")
  private String model;
  @NotNull
  @SerializedName("price")
  private float price;
  @NotNull
  @SerializedName("wiki")
  private String wiki;
  @NotNull
  @SerializedName("img")
  private String img;


  @Convert(converter = CarTypeConverter.class, columnType = String.class)
  private CarType productType;

  public CarEntity(Long id) {
    this.id = id;
  }

  public CarEntity() {
  }

  @Generated(hash = 1015056206)
  public CarEntity(Long id, @NotNull String manufacturer, @NotNull String model,
          float price, @NotNull String wiki, @NotNull String img,
          CarType productType) {
      this.id = id;
      this.manufacturer = manufacturer;
      this.model = model;
      this.price = price;
      this.wiki = wiki;
      this.img = img;
      this.productType = productType;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getManufacturer() {
    return manufacturer;
  }

  public void setManufacturer(String manufacturer) {
    this.manufacturer = manufacturer;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public float getPrice() {
    return price;
  }

  public void setPrice(float price) {
    this.price = price;
  }

  public String getWiki() {
    return wiki;
  }

  public void setWiki(String wiki) {
    this.wiki = wiki;
  }

  public String getImg() {
    return img;
  }

  public void setImg(String img) {
    this.img = img;
  }

  public CarType getProductType() {
    return productType;
  }

  public void setProductType(CarType productType) {
    this.productType = productType;
  }
}
