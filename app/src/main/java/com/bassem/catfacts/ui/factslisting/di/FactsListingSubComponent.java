package com.bassem.catfacts.ui.factslisting.di;

import com.bassem.catfacts.ui.factslisting.view.FactsListingFragment;

import dagger.Subcomponent;

/**
 * Created by mab on 11/13/17.
 * sub component for facts listing
 */

@FactsListingScope
@Subcomponent(modules = {FactsListingModule.class})
public interface FactsListingSubComponent {
    void inject(FactsListingFragment target);
}
