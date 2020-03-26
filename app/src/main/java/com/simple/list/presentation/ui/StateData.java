package com.simple.list.presentation.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class StateData<T> {

  @NonNull private DataStatus status;

  @Nullable private T data;

  @Nullable private Throwable error;

  public StateData<T> loading() {
    this.status = DataStatus.LOADING;
    this.data = null;
    this.error = null;
    return this;
  }

  public StateData<T> success(@NonNull T data) {
    this.status = DataStatus.SUCCESS;
    this.data = data;
    this.error = null;
    return this;
  }

  public StateData<T> error(@NonNull Throwable error) {
    this.status = DataStatus.ERROR;
    this.data = null;
    this.error = error;
    return this;
  }

  @NonNull
  public DataStatus getStatus() {
    return status;
  }

  @Nullable
  public T getData() {
    return data;
  }

  @Nullable
  public Throwable getError() {
    return error;
  }

  public enum DataStatus {
    SUCCESS,
    ERROR,
    LOADING
  }
}