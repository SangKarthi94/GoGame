package com.gogame.mycontacts.ui.contact.create

interface CreateContactListener {
    fun onStarted()
    fun onSuccess(data: Long)
    fun onNavGroup()
    fun onFailure(message : String)
}