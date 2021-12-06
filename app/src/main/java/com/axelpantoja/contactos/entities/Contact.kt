package com.axelpantoja.contactos.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = Contact.TABLE_NAME)
data class Contact(
    @ColumnInfo(name = "first_name") @NotNull val firstName: String,
    @ColumnInfo(name = "last_name") val lastName: String? = null,
    @ColumnInfo(name = "phone") @NotNull val phone: String,
    @ColumnInfo(name = "email") val email: String = "",
    @ColumnInfo(name = "is_favorite") @NotNull val isFavorite: Boolean = false,
    @ColumnInfo(name = "photo_url") val photoUrl: String = ""
) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id = 0

    companion object {
        const val TABLE_NAME = "Contacts"
    }

}
