package com.bassem.catfacts.ui.factslisting.view

import com.bassem.catfacts.utils.BaseView

/**
 * Created by mab on 11/13/17.
 * view for cat facts listing
 */

interface FactsListingView : BaseView {
    fun showNoFacts()
    fun showError()
}