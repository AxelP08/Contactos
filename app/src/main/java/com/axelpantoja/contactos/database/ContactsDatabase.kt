package com.axelpantoja.contactos.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.axelpantoja.contactos.dao.ContactDao
import com.axelpantoja.contactos.entities.Contact

@Database(entities = [Contact::class], version = 1)
abstract class ContactsDatabase : RoomDatabase() {

    abstract fun contactDao(): ContactDao

    //La base de datos se maneja como Singleton
    companion object {

        private const val DATABASE_NAME = "Contacts_database"

        @Volatile
        private var INSTANCE: ContactsDatabase? = null

        fun getInstance(context: Context): ContactsDatabase? {

            INSTANCE ?: synchronized(this) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    ContactsDatabase::class.java,
                    DATABASE_NAME
                ).allowMainThreadQueries().build()
            }

            return INSTANCE

        }

    }

}
