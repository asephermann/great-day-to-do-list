package com.asephermann.greatdaytodolist.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CategoryIcon (
    var icon: Int
) :Parcelable