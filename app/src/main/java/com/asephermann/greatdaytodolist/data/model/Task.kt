package com.asephermann.greatdaytodolist.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "task")
@Parcelize
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val categoryName: String,
    val iconId: Int,
    val desc: String = "",
    val dateStart: String,
    val dateEnds: String,
    val isDone: Boolean = false
):Parcelable
