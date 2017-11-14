package com.bassem.catfacts.ui.factslisting.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by mab on 11/12/17.
 * cat fact model
 */
data class CatFact(
        @SerializedName("fact") var fact: String?,
        @SerializedName("length") var length: Int) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readInt()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(fact)
        parcel.writeInt(length)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CatFact> {
        override fun createFromParcel(parcel: Parcel): CatFact {
            return CatFact(parcel)
        }

        override fun newArray(size: Int): Array<CatFact?> {
            return arrayOfNulls(size)
        }
    }
}