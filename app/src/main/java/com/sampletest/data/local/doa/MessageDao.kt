package com.sampletest.data.local.doa

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sampletest.data.local.entities.Message


@Dao
interface MessageDao {

    @Query("SELECT * FROM Message where createdBy like :createdBy ORDER BY id desc")
    fun getAllMessageByCreated(createdBy: String): LiveData<List<Message>>

    @Query("SELECT * FROM Message ORDER BY id desc")
    fun getAllMessages(): LiveData<List<Message>>

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Insert
    fun insertMessage(message: Message)

}
