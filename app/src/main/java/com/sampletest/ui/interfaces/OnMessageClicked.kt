package com.sampletest.ui.interfaces

import com.sampletest.data.local.entities.Message


interface OnMessageClicked {
    fun onMessageClicked(message: Message)
}