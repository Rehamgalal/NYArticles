package com.nytimes.mostpopular.db

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "article")
data class ArticleEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val title: String,
    val media: String,
    val abstractField : String,
    val published_date:String
    ):Parcelable
