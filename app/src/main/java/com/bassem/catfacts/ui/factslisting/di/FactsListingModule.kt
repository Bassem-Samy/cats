package com.bassem.catfacts.ui.factslisting.di

import android.content.Context
import com.bassem.catfacts.ui.factslisting.interactor.FactsListingInteractor
import com.bassem.catfacts.ui.factslisting.interactor.FactsListingInteractorImpl
import com.bassem.catfacts.ui.factslisting.network.FactsService
import com.bassem.catfacts.ui.factslisting.presenter.FactsListingPresenter
import com.bassem.catfacts.ui.factslisting.presenter.FactsListingPresenterImpl

import com.bassem.catfacts.ui.factslisting.view.FactsListingView

import java.lang.ref.WeakReference

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

/**
 * Created by mab on 11/13/17.
 * facts listing module that provides dependencies for facts listing
 */
@Module
class FactsListingModule(private val mView: FactsListingView, context: Context) {
    private val contextWeakReference: WeakReference<Context> = WeakReference(context)
    // provides context for initializing the presenter
    @FactsListingScope
    @Provides
    fun providesContext(): Context? = contextWeakReference?.get()

    @FactsListingScope
    @Provides
    fun providesFactsListingView(): FactsListingView? = mView

    @FactsListingScope
    @Provides
    fun providesFactService(retrofit: Retrofit): FactsService = retrofit.create(FactsService::class.java)

    @FactsListingScope
    @Provides
    fun providesFactsListingInteractor(service: FactsService): FactsListingInteractor = FactsListingInteractorImpl(service)

    @FactsListingScope
    @Provides
    fun providesFactsListingPresenter(view: FactsListingView, interactor: FactsListingInteractor, context: Context): FactsListingPresenter = FactsListingPresenterImpl(view, interactor, context)
}
