package com.nytimes.mostpopular.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nytimes.mostpopular.db.DB.DATABASE_NAME
import com.nytimes.mostpopular.db.DB.DATABASE_VERSION

@Database(entities = [ArticleEntity::class], version = DATABASE_VERSION, exportSchema = false)
abstract class ArticlesDatabase : RoomDatabase() {

    abstract fun articlesDao(): ArticlesDao

    companion object {
        fun buildDatabase(context: Context): ArticlesDatabase {
            return Room.databaseBuilder(context, ArticlesDatabase::class.java, DATABASE_NAME)
                .build()
        }
    }
}