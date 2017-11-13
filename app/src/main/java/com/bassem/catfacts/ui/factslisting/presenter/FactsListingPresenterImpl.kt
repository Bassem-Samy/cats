package com.bassem.catfacts.ui.factslisting.presenter

import android.content.Context
import com.bassem.catfacts.ui.factslisting.interactor.FactsListingInteractor
import com.bassem.catfacts.ui.factslisting.models.CatFactsResponse
import com.bassem.catfacts.ui.factslisting.view.FactsListingView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.lang.ref.WeakReference

/**
 * Created by mab on 11/13/17.
 * implementation of facts listing presenter
 */

class FactsListingPresenterImpl(val factsView: FactsListingView,
                                val factsInteractor: FactsListingInteractor,
                                context: Context) : FactsListingPresenter {
    val contextWeakReference = WeakReference(context)
    var requestDisposable: Disposable? = null
    var pageSize: Int = 0
    var maxLength: Int = 0
    var lastPage: Int = Int.MAX_VALUE
    var currentPage = 0
    var isLoading: Boolean = false
    override fun loadMoreItems() {
        if (!canLoadMore()) {
            return
        }
        disposeRequest()
        currentPage++
        isLoading = true
        factsInteractor.getCatFacts(maxLength, pageSize, currentPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ res: CatFactsResponse ->
                    isLoading = false
                    if (res != null) {
                        lastPage = res.lastPage
                        //update view

                    }
                }, {
                    isLoading = false
                    factsView.showError()
                })
    }

    private fun canLoadMore(): Boolean = currentPage < lastPage && !isLoading

    private fun disposeRequest() {

        requestDisposable?.dispose()
    }


    override fun onDestroy() {
        disposeRequest()
    }

    override fun updateFactsLengthValues(maxLength: Int, pageSize: Int) {
        this.pageSize = pageSize
        this.maxLength = maxLength
        this.lastPage = Int.MAX_VALUE
        this.currentPage = 0


    }
}