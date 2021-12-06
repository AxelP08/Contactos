package com.axelpantoja.contactos

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.ViewModelProvider
import com.axelpantoja.contactos.adapter.ContactsAdapter
import com.axelpantoja.contactos.adapter.FavoritesAdapter
import com.axelpantoja.contactos.databinding.ActivityMainBinding
import com.axelpantoja.contactos.viewmodel.ContactsViewModel
import com.axelpantoja.contactos.viewmodel.ContactsViewModelFactory

class MainActivity : AppCompatActivity() {

    //Contiene la vista inflada para acceder a los ID's
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Inflar la clase de ViewBinding, obtener la vista raiz y establecerla como vista de la actividad
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //Crear los adaptadores
        val favoritesAdapter = FavoritesAdapter { contact, sharedView ->
            val goDetail = Intent(this, ContactDetailActivity::class.java)
            goDetail.putExtra("id", contact.id)

            val options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(this, sharedView,
                    sharedView.transitionName)

            startActivity(goDetail, options.toBundle())
        }

        val contactsAdapter = ContactsAdapter({ contact, sharedView ->
            //onClick
            val goDetail = Intent(this, ContactDetailActivity::class.java)
            goDetail.putExtra("id", contact.id)

            val options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(this, sharedView,
                    sharedView.transitionName)

            startActivity(goDetail, options.toBundle())

        }, { phone ->
            //onCallClicked

            val call = Intent(Intent.ACTION_DIAL, Uri.parse("tel: $phone"))
            startActivity(call)

        })

        //Crear el ViewModel
        val contactsViewModel = ViewModelProvider(
            this,
            ContactsViewModelFactory(application)
        ).get(ContactsViewModel::class.java)

        //Observar la lista de favoritos
        contactsViewModel.getFavorites().observe(this)
        { contacts ->

            favoritesAdapter.favorites = contacts
            binding.rvFavorites.adapter = favoritesAdapter
            favoritesAdapter.notifyDataSetChanged()

        }

        //Observar la lista de contactos
        contactsViewModel.getContacts().observe(this)
        { contacts ->

            contactsAdapter.contacts = contacts
            binding.rvContacts.adapter = contactsAdapter
            contactsAdapter.notifyDataSetChanged()

        }

        //Click al botón agregar
        binding.fabAdd.setOnClickListener {

            val goAdd = Intent(this, AddContactActivity::class.java)
            startActivity(goAdd)

        }

    }

}
