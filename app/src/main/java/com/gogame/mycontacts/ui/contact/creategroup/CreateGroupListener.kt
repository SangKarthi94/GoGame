package com.gogame.mycontacts.ui.contact.creategroup

interface CreateGroupListener {
    fun onStarted()
    fun onSuccess()
    fun onFailure(message : String)
    fun onRemove()
}