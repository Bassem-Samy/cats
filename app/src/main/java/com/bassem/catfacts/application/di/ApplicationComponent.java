package com.bassem.catfacts.application.di;

import com.bassem.catfacts.ui.factslisting.di.FactsListingModule;
import com.bassem.catfacts.ui.factslisting.di.FactsListingScope;
import com.bassem.catfacts.ui.factslisting.di.FactsListingSubComponent;

import javax.inject.Singleton;

import dagger.Component;
import dagger.Module;

/**
 * Created by mab on 11/12/17.
 * Component for Application di
 */
@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {
    FactsListingSubComponent plus(FactsListingModule module);
}
