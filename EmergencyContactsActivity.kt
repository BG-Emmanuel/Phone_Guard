package com.phoneguard.pro.ui

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.*
import com.phoneguard.pro.data.AlertRepository
import com.phoneguard.pro.data.EmergencyContact
import com.phoneguard.pro.databinding.ActivityEmergencyContactsBinding
import com.phoneguard.pro.databinding.ItemContactBinding
import kotlinx.coroutines.launch

class EmergencyContactsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEmergencyContactsBinding
    private lateinit var repository: AlertRepository
    private lateinit var adapter: ContactAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmergencyContactsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repository = AlertRepository(this)
        adapter = ContactAdapter { contact ->
            // Delete on swipe or button tap
            lifecycleScope.launch { repository.deleteContact(contact) }
        }

        binding.contactsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.contactsRecyclerView.adapter = adapter

        repository.allContacts.observe(this) { contacts ->
            adapter.submitList(contacts)
        }

        binding.btnAddContact.setOnClickListener {
            val name = binding.contactNameInput.text.toString().trim()
            val number = binding.contactNumberInput.text.toString().trim()

            if (name.isNotEmpty() && number.isNotEmpty()) {
                lifecycleScope.launch {
                    repository.insertContact(EmergencyContact(
                        name = name, phoneNumber = number
                    ))
                }
                binding.contactNameInput.text?.clear()
                binding.contactNumberInput.text?.clear()
            }
        }
    }
}

class ContactAdapter(
    private val onDelete: (EmergencyContact) -> Unit
) : ListAdapter<EmergencyContact, ContactAdapter.ViewHolder>(
    object : DiffUtil.ItemCallback<EmergencyContact>() {
        override fun areItemsTheSame(a: EmergencyContact, b: EmergencyContact) =
            a.id == b.id
        override fun areContentsTheSame(a: EmergencyContact, b: EmergencyContact) =
            a == b
    }
) {
    inner class ViewHolder(val binding: ItemContactBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(ItemContactBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contact = getItem(position)
        holder.binding.apply {
            contactInitial.text = contact.name.first().uppercase()
            contactName.text = contact.name
            contactNumber.text = contact.phoneNumber
            btnDeleteContact.setOnClickListener { onDelete(contact) }
        }
    }
}
