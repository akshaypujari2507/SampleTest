package com.sampletest.ui.viewmodel

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.sampletest.data.local.entities.Message
import com.sampletest.data.repo.Repository

class ListActivityViewModel(private val repo: Repository) : ViewModel() {

    var messages: LiveData<List<Message>>? = null

    suspend fun getAllEmployees(): LiveData<List<Message>> {
        if (messages == null) {
            try {
                messages = repo.getAllMessages()
            } catch (e: Exception) {
                println(e)
            }
        }
        return messages!!
    }



}