package com.bassem.catfacts.utils;

/**
 * Created by mab on 11/13/17.
 * Base view that has basic functionalities like loading and showing no internet connection
 */

public interface BaseView {
    void showLoading();

    void hideLoading();

    void showNoInternetConnection();
}
