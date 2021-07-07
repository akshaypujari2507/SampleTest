package com.sampletest.data.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sampletest.data.local.db.AppDatabase
import com.sampletest.data.local.entities.Message
import com.sampletest.data.remote.api.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class Repository(val db: AppDatabase, val api: ApiService) {

    lateinit var messages: LiveData<List<Message>>


    fun getRemoteRecord(createdBy:String): LiveData<String> {


//        var remoteMessage: String = ""
        val remoteMessage: MutableLiveData<String> = MutableLiveData<String>()

        GlobalScope.launch(Dispatchers.IO) {
            val response = api.getResponse().execute().body()
            remoteMessage.postValue(response)
//            insertRecords(response!!)
//            remoteMessage = response.toString()
            createMessage(response?.replace("message: ", "")!!, createdBy)
        }



        return remoteMessage!!
    }

    fun insertRecords(message: Message) {

        GlobalScope.launch(Dispatchers.IO) {
            db.messageDao().insertMessage(message)
        }

    }

    fun createMessage(messageStr: String, createdBy: String) {
        val sdf = SimpleDateFormat("dd-MM-yyyy hh:mm:ss a")
        val netDate = Date(System.currentTimeMillis())
        val dateTime =sdf.format(netDate)
        println(dateTime)


        val message: Message = Message()
        message.msg = messageStr
        message.createdBy = createdBy
        message.createdOn = dateTime

        val tt = insertRecords(message)
        println(tt)

    }

    suspend fun getAllMessages(): LiveData<List<Message>> {

        withContext(Dispatchers.IO) {
            try {
                messages = db.messageDao().getAllMessages()
            } catch (e: Exception) {
                println(e)
            }
        }
        return messages
    }


}