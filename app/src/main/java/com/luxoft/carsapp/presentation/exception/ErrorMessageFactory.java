package com.luxoft.carsapp.presentation.exception;

import android.content.Context;

import com.luxoft.carsapp.R;
import com.luxoft.carsapp.data.exception.NetworkConnectionException;

public class ErrorMessageFactory {

  private ErrorMessageFactory() {
  }

  public static String create(Context context, Exception exception) {
    String message = context.getString(R.string.exception_message_generic);

    if (exception instanceof NetworkConnectionException) {
      message = context.getString(R.string.exception_message_no_connection);
    } else if (exception instanceof ClassNotFoundException) {
      message = context.getString(R.string.exception_message_product_not_found);
    }

    return message;
  }
}
