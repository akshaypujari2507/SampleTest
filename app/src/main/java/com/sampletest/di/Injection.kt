package com.sampletest.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.sampletest.data.local.db.AppDatabase
import com.sampletest.data.remote.api.ApiClient
import com.sampletest.data.repo.Repository
import com.sampletest.ui.viewmodel.factory.ViewModelListActivityFactory
import com.sampletest.ui.viewmodel.factory.ViewModelMainActivityFactory


object Injection {

    var repo: Repository? = null

    //repo provider
    private fun provideRepository(context: Context): Repository {
        val database = AppDatabase.getInstance(context)
        val api = ApiClient.api

        if (repo == null) {
            synchronized(Repository::class.java) {
                if (repo == null) {
                    repo = Repository(database, api)
                }
            }
        }
        return repo!!
    }

    // main activity viewmodel provider
    fun provideMainActivityViewModelFactory(context: Context): ViewModelProvider.Factory {
        return ViewModelMainActivityFactory(
            provideRepository(
                context
            )
        )
    }

    // list activity viewmodel provider
    fun provideListActivityViewModelFactory(context: Context): ViewModelProvider.Factory {
        return ViewModelListActivityFactory(
            provideRepository(
                context
            )
        )
    }

}