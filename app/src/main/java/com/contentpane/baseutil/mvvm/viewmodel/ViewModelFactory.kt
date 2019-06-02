package com.contentpane.baseutil.mvvm.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.contentpane.baseutil.mvvm.Injection
import com.contentpane.baseutil.mvvm.repository.Repository

class ViewModelFactory private constructor(
    private val repository: Repository

) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        with(modelClass) {
            when {
                isAssignableFrom(TestViewModel::class.java) -> {
                    TestViewModel(repository)
                }
                else ->
                    throw IllegalArgumentException("Unknown ViewModel: ${modelClass.name}")
            }
        } as T


    companion object {
        private var INSTANCE: ViewModelFactory? = null
        fun getInstance(application: Application) =
            INSTANCE ?: synchronized(ViewModelFactory::class.java) {
                INSTANCE ?: ViewModelFactory(
                    Injection.provideRepository(application)
                )
            }
    }
}