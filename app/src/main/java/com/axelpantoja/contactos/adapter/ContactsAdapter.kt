package com.axelpantoja.contactos.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.axelpantoja.contactos.R
import com.axelpantoja.contactos.databinding.ContactsItemBinding
import com.axelpantoja.contactos.entities.Contact
import com.bumptech.glide.Glide

class ContactsAdapter(
    val onClick: (contact: Contact, view: View) -> Unit,
    val onCallClicked: (phone: String) -> Unit
) :
    RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder>() {

    lateinit var contacts: List<Contact>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        return ContactsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.contacts_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        holder.bindItem(contacts[position])
    }

    override fun getItemCount() = contacts.size

    inner class ContactsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = ContactsItemBinding.bind(itemView)

        fun bindItem(contact: Contact) {

            binding.tvName.text = "${contact.firstName} ${contact.lastName}"
            binding.tvPhone.text = contact.phone
            binding.tvEmail.text = contact.email

            if (contact.photoUrl.isNotEmpty()) {
                Glide.with(binding.ivPhoto).load(contact.photoUrl).centerCrop()
                    .placeholder(R.drawable.ic_person).into(binding.ivPhoto)
            } else {
                binding.ivPhoto.setImageResource(R.drawable.ic_person)
            }

            binding.ivPhoto.transitionName = "transition${contact.id}"

            itemView.setOnClickListener { onClick(contact, binding.ivPhoto) }

            binding.btnCall.setOnClickListener { onCallClicked(contact.phone) }

        }

    }

}
