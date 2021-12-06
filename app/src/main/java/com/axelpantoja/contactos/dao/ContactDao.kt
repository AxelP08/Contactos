package com.axelpantoja.contactos.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.axelpantoja.contactos.entities.Contact

@Dao
interface ContactDao {

    @Insert
    fun insert(contact: Contact)

    @Update
    fun update(contact: Contact)

    @Delete
    fun delete(contact: Contact)

    @Query("SELECT * FROM ${Contact.TABLE_NAME} ORDER BY first_name, last_name")
    fun getAllOrdered(): LiveData<List<Contact>>

    @Query("SELECT * FROM ${Contact.TABLE_NAME} WHERE id = :id")
    fun getById(id: Int): LiveData<Contact>

    @Query("SELECT * FROM ${Contact.TABLE_NAME} WHERE is_favorite = 1")
    fun getAllFavorites(): LiveData<List<Contact>>

}
