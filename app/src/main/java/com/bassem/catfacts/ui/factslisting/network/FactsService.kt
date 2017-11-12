package com.bassem.catfacts.ui.factslisting.network

import com.bassem.catfacts.ui.factslisting.models.CatFactsResponse
import io.reactivex.Single
import retrofit2.adapter.rxjava2.Result
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by mab on 11/12/17.
 * interface for cat facts network calls
 */
interface FactsService {
    @GET("facts")
    fun getFacts(@Query("max_length") maxLength: Int, @Query("limit") limit: Int, @Query("page") page: Int): Single<CatFactsResponse>
}