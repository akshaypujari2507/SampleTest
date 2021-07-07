package com.sampletest.data.local.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Message", indices = [Index(value = ["msg","createdOn"], unique = true)])
class Message {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

    var msg: String? = null
    var createdOn: String? = null
    var createdBy: String? = null

    override fun equals(other: Any?): Boolean {
        return id == other
    }

    override fun hashCode(): Int {
        return id!!
    }

    override fun toString(): String {
        return "Message(msg=$msg, createdOn=$createdOn, createdBy=$createdBy)"
    }
}