package com.bassem.catfacts.ui.factslisting.interactor

import com.bassem.catfacts.ui.factslisting.models.CatFactsResponse
import io.reactivex.Single

/**
 * Created by mab on 11/13/17.
 * Interactor interface for facts listing
 */

interface FactsListingInteractor {
    fun getCatFacts(maxLength: Int, limit: Int, page: Int): Single<CatFactsResponse>
}
