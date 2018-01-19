package com.luxoft.carsapp.domain.exception;

public interface ErrorBundle {
  Exception getException();

  String getErrorMessage();
}
