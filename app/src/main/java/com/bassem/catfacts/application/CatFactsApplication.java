package com.bassem.catfacts.application;

import android.app.Application;

/**
 * Created by mab on 11/12/17.
 * Cat facts application class
 */


public class CatFactsApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static CatFactsApplication get(Application application) {
        return (CatFactsApplication) application;
    }
}
