package com.bassem.catfacts.application;

import android.app.Application;

import com.bassem.catfacts.application.di.ApplicationComponent;
import com.bassem.catfacts.application.di.ApplicationModule;
import com.bassem.catfacts.application.di.DaggerApplicationComponent;
import com.bassem.catfacts.utils.Constants;

/**
 * Created by mab on 11/12/17.
 * Cat facts application class
 */


public class CatFactsApplication extends Application {
    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initAppComponent();
    }

    private void initAppComponent() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this, Constants.BASE_URL))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return this.applicationComponent;
    }

    public static CatFactsApplication get(Application application) {
        return (CatFactsApplication) application;
    }
}
