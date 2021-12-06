package com.axelpantoja.contactos

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.axelpantoja.contactos.databinding.ActivityAddContactBinding
import com.axelpantoja.contactos.entities.Contact
import com.axelpantoja.contactos.viewmodel.ContactsViewModel
import com.axelpantoja.contactos.viewmodel.ContactsViewModelFactory

class AddContactActivity : AppCompatActivity() {

    //Contiene la vista inflada para acceder a los ID's
    private lateinit var binding: ActivityAddContactBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Inflar la clase de ViewBinding, obtener la vista raiz y establecerla como vista de la actividad
        binding = ActivityAddContactBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setSupportActionBar(binding.tbToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //Crear el ViewModel
        val contactsViewModel = ViewModelProvider(
            this,
            ContactsViewModelFactory(application)
        ).get(ContactsViewModel::class.java)

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

                    contactsViewModel.insertContact(contact)

                    onBackPressed()

                }
            }

        }

        //Click al botón cancelar
        binding.btnCancel.setOnClickListener {
            onBackPressed()
        }

    }

}
