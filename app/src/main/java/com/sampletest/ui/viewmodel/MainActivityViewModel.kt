package com.sampletest.ui.viewmodel

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.sampletest.data.repo.Repository

class MainActivityViewModel(private val repo: Repository) : ViewModel() {

    var message: LiveData<String>? = null
//    var message: String? = null

    fun getMessageRemote(createdBy: String): LiveData<String> {
//        if (message == null) {
            try {
                message = repo.getRemoteRecord(createdBy)
            } catch (e: Exception) {
                println(e)
            }
//        }
        return message!!
    }

    fun setMessageLocal(message: String, createdBy: String) {
        try {
            repo.createMessage(message, createdBy)
        } catch (e: Exception) {
            println(e)
        }
    }



}