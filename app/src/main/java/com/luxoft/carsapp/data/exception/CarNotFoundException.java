package com.luxoft.carsapp.data.exception;

public class CarNotFoundException extends Exception {

  public CarNotFoundException() {
    super();
  }

  public CarNotFoundException(final String message) {
    super(message);
  }

  public CarNotFoundException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public CarNotFoundException(final Throwable cause) {
    super(cause);
  }
}
