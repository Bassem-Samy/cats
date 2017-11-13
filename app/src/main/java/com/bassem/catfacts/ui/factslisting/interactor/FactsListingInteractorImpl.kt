package com.bassem.catfacts.ui.factslisting.interactor

import com.bassem.catfacts.ui.factslisting.models.CatFactsResponse
import com.bassem.catfacts.ui.factslisting.network.FactsService
import io.reactivex.Single

/**
 * Created by mab on 11/13/17.
 * implementation for facts listing interactor
 */

class FactsListingInteractorImpl(val factsService: FactsService) : FactsListingInteractor {
    override fun getCatFacts(maxLength: Int, limit: Int, page: Int): Single<CatFactsResponse> = factsService.getFacts(maxLength, limit, page)
}
