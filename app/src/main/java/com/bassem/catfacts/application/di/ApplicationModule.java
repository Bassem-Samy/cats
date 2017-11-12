package com.bassem.catfacts.application.di;

import android.app.Application;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mab on 11/12/17.
 * App module that provides dependencies that will be used globally in the app
 */

@Module
public class ApplicationModule {
    private Application application;
    private String mBaseUrl;

    public ApplicationModule(Application application, String baseUrl) {
        this.application = application;
        this.mBaseUrl = baseUrl;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return application;
    }

    @Singleton
    @Provides
    Converter.Factory providesConverterFactory() {
        return GsonConverterFactory.create();
    }

    @Singleton
    @Provides
    CallAdapter.Factory providesCallAdapterFactory() {
        return RxJava2CallAdapterFactory.create();
    }

    @Singleton
    @Provides
    Retrofit providesRetrofit(Converter.Factory converterFactory, CallAdapter.Factory callAdapterFactory) {
        return new Retrofit.Builder().
                addCallAdapterFactory(callAdapterFactory)
                .addConverterFactory(converterFactory)
                .baseUrl(mBaseUrl)
                .build();
    }

}
