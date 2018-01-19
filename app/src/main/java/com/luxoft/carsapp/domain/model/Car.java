package com.luxoft.carsapp.domain.model;

public class Car {
  private String manufacturer;
  private String model;
  private float price;
  private String wiki;
  private String img;

  public Car() {
  }

  public Car(String model) {
    this.model = model;
  }

  public Car(String manufacturer, String model, float price, String wiki, String img) {
    this.manufacturer = manufacturer;
    this.model = model;
    this.price = price;
    this.wiki = wiki;
    this.img = img;
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
}
