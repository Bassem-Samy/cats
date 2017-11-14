package com.bassem.catfacts.ui.factslisting.presenter

import android.content.Context
import com.bassem.catfacts.ui.factslisting.interactor.FactsListingInteractor
import com.bassem.catfacts.ui.factslisting.models.CatFactsResponse
import com.bassem.catfacts.ui.factslisting.view.FactsListingView
import com.bassem.catfacts.utils.NetworkHelper
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
                                context: Context?) : FactsListingPresenter {
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
        if (!NetworkHelper.hasInternet(contextWeakReference.get())) {
            if (currentPage == 0) {
                factsView.showNoInternetConnection()
            } else {
                factsView.showCantLoadMoreNoInternet()
            }
            return
        }
        currentPage++

        isLoading = true
        if (currentPage == 1) {
            factsView.showLoading()
        } else {
            factsView.showLoadingMore();
        }
        factsInteractor.getCatFacts(maxLength, pageSize, currentPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ res: CatFactsResponse ->

                    factsView.hideLoading()
                    isLoading = false
                    if (res != null && res.data?.isNotEmpty()) {
                        lastPage = res.lastPage
                        //update view
                        factsView.updateFacts(res.data)


                    } else {
                        factsView.showNoFacts()
                    }
                }, {

                    factsView.hideLoading()
                    isLoading = false
                    factsView.showError()
                })
    }

    private fun canLoadMore(): Boolean = currentPage < lastPage && !isLoading && pageSize > 0

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