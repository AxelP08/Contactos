package com.axelpantoja.contactos.repository

import android.app.Application
import com.axelpantoja.contactos.dao.ContactDao
import com.axelpantoja.contactos.database.ContactsDatabase
import com.axelpantoja.contactos.entities.Contact

class ContactsRepository(application: Application) {

    private val contactDao: ContactDao? = ContactsDatabase.getInstance(application)?.contactDao()

    //Insertar el contacto
    fun insert(contact: Contact) {

        contactDao?.insert(contact)

    }

    //Actualizar el contacto
    fun update(contact: Contact) {

        contactDao?.update(contact)

    }

    //Eliminar el contacto
    fun delete(contact: Contact) {

        contactDao?.delete(contact)

    }

    //Obtener todos los contactos
    fun getContacts() = contactDao?.getAllOrdered()

    //Obtener un contacto por id
    fun getContactById(id: Int) = contactDao?.getById(id)

    //Obtener todos los favoritos
    fun getAllFavorites() = contactDao?.getAllFavorites()

}
