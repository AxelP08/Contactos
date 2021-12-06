package com.axelpantoja.contactos.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.axelpantoja.contactos.entities.Contact
import com.axelpantoja.contactos.repository.ContactsRepository

class ContactsViewModel(application: Application) : ViewModel() {

    private val repo = ContactsRepository(application)

    //Insertar
    fun insertContact(contact: Contact) {
        repo.insert(contact)
    }

    //Actualizar
    fun updateContact(contact: Contact) {
        repo.update(contact)
    }

    //Elmiminar
    fun deleteContact(contact: Contact) {
        repo.delete(contact)
    }

    //Obtener todos
    fun getContacts(): LiveData<List<Contact>> {
        return repo.getContacts()!!
    }

    //Obtener por id
    fun getContactById(id: Int): LiveData<Contact> {
        return repo.getContactById(id)!!
    }

    //Obtener los favoritos
    fun getFavorites(): LiveData<List<Contact>> {
        return repo.getAllFavorites()!!
    }

}
