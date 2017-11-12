package com.bassem.catfacts.ui.factslisting.models

import com.google.gson.annotations.SerializedName

/**
 * Created by mab on 11/12/17.
 * cat fact model
 */
data class CatFact(
        @SerializedName("fact") var fact: String?,
        @SerializedName("length") var length: Int)