package com.gogame.mycontacts.ui.contact.create

import android.app.Application
import android.view.View
import android.widget.CompoundButton
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.gogame.mycontacts.data.db.AppDatabase
import com.gogame.mycontacts.data.db.UserDao
import com.gogame.mycontacts.data.db.entities.User
import com.gogame.mycontacts.ui.contact.contactlist.ContactRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class CreateContactViewModel(application: Application) : AndroidViewModel(application) {

    var image: String? = null
    var name: String = ""
    var phone: String = ""
    var email: String? = null
    var isChecked: Boolean = false

    var createContactListener: CreateContactListener? = null

    private var repository: ContactRepository

    init {
        val userDao: UserDao = AppDatabase.invoke(context = application).getUserDao()
        repository = ContactRepository(userDao)
    }

    fun onCreateContact(view: View) {
        if (!checkData()) {
            createContactListener?.onFailure("Invalid Data")
            return
        }

        val user = User(0, name, email = email, phone, image, isChecked)

        //Run in the background thread instead of main thread
        viewModelScope.launch(Dispatchers.IO) {
            var data = repository.addUser(user)
            if (data.toInt() > 0) {
                createContactListener?.onSuccess(data)
            }
        }
    }

    fun onNavigateGroup(view: View) {
        createContactListener?.onNavGroup()
    }

    fun onCheckedChange(button: CompoundButton?, check: Boolean) {
        isChecked = check
    }

    private fun checkData(): Boolean {
        return !(name.isNullOrEmpty() || phone.isNullOrEmpty())
    }


}