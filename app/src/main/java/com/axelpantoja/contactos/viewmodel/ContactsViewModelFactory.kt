package com.axelpantoja.contactos.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ContactsViewModelFactory(val application: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ContactsViewModel::class.java)) {
            ContactsViewModel(application) as T
        } else {
            throw IllegalArgumentException()
        }
    }

}
