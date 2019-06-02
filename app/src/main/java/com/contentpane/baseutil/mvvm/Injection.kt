package com.contentpane.baseutil.mvvm

import android.content.Context
import com.contentpane.baseutil.mvvm.repository.Repository

object Injection {

    fun provideRepository(context: Context): Repository {
//        val database = DataBase.getInstance(context)
//        return Repository.getInstance(
//            RemoteDataSource.getInstance(),
//            LocalDataSource.getInstance(database.xxxDao())
//        )
        return Repository()
    }

//    fun providePagingRepository(): PagingRepository {
//
//    }

//    fun provideSearchRepository(): SearchRepository {
//
//    }

//    fun provideService(): ApiService {
//
//    }
}