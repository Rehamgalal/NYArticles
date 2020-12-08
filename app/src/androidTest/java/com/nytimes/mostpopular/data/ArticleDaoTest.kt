package com.nytimes.mostpopular.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.nytimes.mostpopular.db.ArticleEntity
import com.nytimes.mostpopular.db.ArticlesDao
import com.nytimes.mostpopular.db.ArticlesDatabase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class ShoppingDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: ArticlesDatabase
    private lateinit var dao: ArticlesDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ArticlesDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.articlesDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertArticleItem() = runBlockingTest {
        val articleItem = ArticleEntity(0, "todaynew", "https://www.nytimes.com/2020/12/07/us/trump-covid-vaccine-pfizer.html","the new detail")
        dao.insert(articleItem)

        val allArticleItems = dao.allArticlesEntities()

        assertThat(allArticleItems).contains(articleItem)
    }

}