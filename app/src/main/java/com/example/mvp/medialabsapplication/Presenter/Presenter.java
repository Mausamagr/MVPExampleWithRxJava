package com.example.mvp.medialabsapplication.Presenter;

public interface Presenter<V> {

    void attachView(V view);

    void detachView();

}
