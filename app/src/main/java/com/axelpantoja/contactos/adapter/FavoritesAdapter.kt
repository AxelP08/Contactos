package com.axelpantoja.contactos.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.axelpantoja.contactos.R
import com.axelpantoja.contactos.databinding.FavoritesItemBinding
import com.axelpantoja.contactos.entities.Contact
import com.bumptech.glide.Glide

class FavoritesAdapter(val onClick: (contact: Contact, view: View) -> Unit) :
    RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder>() {

    lateinit var favorites: List<Contact>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        return FavoritesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.favorites_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        holder.bindItem(favorites[position])
    }

    override fun getItemCount() = favorites.size

    inner class FavoritesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = FavoritesItemBinding.bind(itemView)

        fun bindItem(contact: Contact) {

            binding.tvName.text = "${contact.firstName} ${contact.lastName}"

            if (contact.photoUrl.isNotEmpty()) {
                Glide.with(binding.ivPhoto).load(contact.photoUrl).centerCrop()
                    .placeholder(R.drawable.ic_person).into(binding.ivPhoto)
            } else {
                binding.ivPhoto.setImageResource(R.drawable.ic_person)
            }

            binding.ivPhoto.transitionName = "transition${contact.id}"

            itemView.setOnClickListener { onClick(contact, binding.ivPhoto) }

        }

    }

}
