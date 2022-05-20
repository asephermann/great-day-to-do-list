package com.asephermann.greatdaytodolist.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "category")
@Parcelize
data class Category(
    @PrimaryKey (autoGenerate = true) val id: Int = 0,
    val name: String,
    val iconId : Int
): Parcelable
