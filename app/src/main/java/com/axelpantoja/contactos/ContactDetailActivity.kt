package com.axelpantoja.contactos

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.axelpantoja.contactos.databinding.ActivityContactDetailBinding
import com.axelpantoja.contactos.entities.Contact
import com.axelpantoja.contactos.viewmodel.ContactsViewModel
import com.axelpantoja.contactos.viewmodel.ContactsViewModelFactory
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ContactDetailActivity : AppCompatActivity() {

    //Contiene la vista inflada para acceder a los ID's
    private lateinit var binding: ActivityContactDetailBinding

    //Propiedades del contacto
    private var phone = ""
    private var email = ""

    //ViewModel
    private lateinit var contactsViewModel: ContactsViewModel

    //Instancia del contacto
    private lateinit var contact: Contact

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Inflar la clase de ViewBinding, obtener la vista raiz y establecerla como vista de la actividad
        binding = ActivityContactDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //Configurar la barra
        setSupportActionBar(binding.tbToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //Detener las transiciones de entrada hasta cargar la información
        postponeEnterTransition()

        val id = intent.getIntExtra("id", -1)

        binding.ivPhoto.transitionName = "transition$id"

        contactsViewModel = ViewModelProvider(
            this,
            ContactsViewModelFactory(application)
        ).get(ContactsViewModel::class.java)

        contactsViewModel.getContactById(id).observe(this) { contact ->

            if (contact != null) {
                this.contact = contact

                //Cargar la imagen
                Glide.with(this).load(contact.photoUrl).placeholder(R.drawable.ic_person)
                    .centerCrop().into(binding.ivPhoto)

                phone = contact.phone
                email = contact.email

                //Rellenar los datos
                with(binding) {

                    tvName.text = "${contact.firstName} ${contact.lastName}"
                    tvPhone.text = phone
                    tvEmail.text = email

                }

                //Iniciar la transición de entrada
                startPostponedEnterTransition()

            }

        }

        //Click al área de llamar
        binding.clPhone.setOnClickListener {

            val call = Intent(Intent.ACTION_DIAL, Uri.parse("tel: $phone"))
            startActivity(call)

        }

        //Click al área de email
        binding.clEmail.setOnClickListener {

            val email = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto: $email"))

            startActivity(Intent.createChooser(email, getString(R.string.send_email)))

        }

        //Click al botón de editar
        binding.fabEdit.setOnClickListener {

            val goEdit = Intent(this, EditContactActivity::class.java)
            goEdit.putExtra("id", id)

            startActivity(goEdit)

        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.contact_detail_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home) onBackPressed()
        else if (item.itemId == R.id.iDelete) {

            MaterialAlertDialogBuilder(this)
                .setTitle(R.string.delete_confirmation)
                .setNegativeButton(R.string.delete_no, null)
                .setPositiveButton(R.string.delete_yes) { _, _ ->

                    contactsViewModel.deleteContact(contact)
                    onBackPressed()

                }.show()

        }

        return super.onOptionsItemSelected(item)
    }

}
