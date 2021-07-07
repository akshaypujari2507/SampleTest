package com.sampletest.ui.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sampletest.data.repo.Repository
import com.sampletest.ui.viewmodel.ListActivityViewModel

class ViewModelListActivityFactory(private val repository: Repository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListActivityViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ListActivityViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}