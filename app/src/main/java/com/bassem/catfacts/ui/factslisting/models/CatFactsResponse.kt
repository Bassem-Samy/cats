package com.bassem.catfacts.ui.factslisting.models

import com.google.gson.annotations.SerializedName

/**
 * Created by mab on 11/12/17.
 * cat facts response model
 */
data class CatFactsResponse(@SerializedName("data") var data: List<CatFact>,
                            @SerializedName("total") var total: Int,
                            @SerializedName("last_page") var lastPage: Int)