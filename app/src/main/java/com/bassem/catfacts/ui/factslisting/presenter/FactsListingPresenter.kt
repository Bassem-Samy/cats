package com.bassem.catfacts.ui.factslisting.presenter

/**
 * Created by mab on 11/13/17.
 * interface for fact listing presenter
 */
interface FactsListingPresenter {
    fun updateFactsLengthValues(maxLength: Int, pageSize: Int)
    fun loadMoreItems()
    fun onDestroy()
}