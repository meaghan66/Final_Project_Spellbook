package com.spellbookapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.spellbookapp.data.models.Spell
import com.spellbookapp.databinding.ItemSpellBinding

// RecyclerView adapter to show a list of the spells
class SpellAdapter(
    private var spells: List<Spell>,
    private val onItemClick: (Spell) -> Unit
) : RecyclerView.Adapter<SpellAdapter.SpellViewHolder>() {

    // ViewHolder for each spell
    inner class SpellViewHolder(private val binding: ItemSpellBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(spell: Spell) { // Bind the data to the view
            binding.spellNameTextView.text = spell.name
            binding.root.setOnClickListener {
                // Trigger the click listener
                onItemClick(spell)
            }
        }
    }

    // On create viewHolder instance
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpellViewHolder {
        // Inflate the binding
        val binding = ItemSpellBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SpellViewHolder(binding)
    }

    // On binding the ViewHolder
    override fun onBindViewHolder(holder: SpellViewHolder, position: Int) {
        holder.bind(spells[position])
    }

    // Get the number of spells
    override fun getItemCount(): Int = spells.size

    // Update the spells in the adapter
    fun updateSpells(newSpells: List<Spell>) {
        spells = newSpells
        notifyDataSetChanged()
    }
}
