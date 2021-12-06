package com.axelpantoja.contactos

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.axelpantoja.contactos.databinding.ActivityEditContactBinding
import com.axelpantoja.contactos.entities.Contact
import com.axelpantoja.contactos.viewmodel.ContactsViewModel
import com.axelpantoja.contactos.viewmodel.ContactsViewModelFactory


class EditContactActivity : AppCompatActivity() {

    //Contiene la vista inflada para acceder a los ID's
    private lateinit var binding: ActivityEditContactBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Inflar la clase de ViewBinding, obtener la vista raiz y establecerla como vista de la actividad
        binding = ActivityEditContactBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setSupportActionBar(binding.tbToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val id = intent.getIntExtra("id", -1)

        //Crear el ViewModel
        val contactsViewModel = ViewModelProvider(
            this,
            ContactsViewModelFactory(application)
        ).get(ContactsViewModel::class.java)

        //Rellenar con los datos actuales
        contactsViewModel.getContactById(id).observe(this) { contact ->

            if (contact != null) {

                val name = contact.firstName
                val lastName = contact.lastName
                val phone = contact.phone
                val email = contact.email
                val photoUrl = contact.photoUrl
                val isFavorite = contact.isFavorite

                //Rellenar los datos
                with(binding) {

                    etName.setText(name)
                    etLastName.setText(lastName)
                    etPhone.setText(phone)
                    etEmail.setText(email)
                    etPhotoUrl.setText(photoUrl)
                    cbFavorite.isChecked = isFavorite

                }

            }

        }

        //Click al botón guardar
        binding.btnSave.setOnClickListener {

            with(binding) {

                val name = etName.text.toString()
                val lastName = etLastName.text.toString()
                val phone = etPhone.text.toString()
                val email = etEmail.text.toString()
                val photoUrl = etPhotoUrl.text.toString()

                if (name.isNotEmpty() && lastName.isNotEmpty() && phone.isNotEmpty()
                    && phone.isNotEmpty() && email.isNotEmpty()
                ) {

                    val contact = if (photoUrl.isNotEmpty())
                        Contact(name, lastName, phone, email, cbFavorite.isChecked, photoUrl)
                    else Contact(name, lastName, phone, email, cbFavorite.isChecked)

                    contact.id = id

                    contactsViewModel.updateContact(contact)

                    onBackPressed()

                }
            }

        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home) onBackPressed()

        return super.onOptionsItemSelected(item)
    }

}
