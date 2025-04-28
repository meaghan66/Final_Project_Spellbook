package com.spellbookapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.spellbookapp.data.models.Spell
import com.spellbookapp.databinding.ItemSpellBinding

class SpellAdapter(
    private var spells: List<Spell>,
    private val onItemClick: (Spell) -> Unit
) : RecyclerView.Adapter<SpellAdapter.SpellViewHolder>() {

    inner class SpellViewHolder(private val binding: ItemSpellBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(spell: Spell) {
            binding.spellNameTextView.text = spell.name
            binding.root.setOnClickListener {
                onItemClick(spell)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpellViewHolder {
        val binding = ItemSpellBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SpellViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SpellViewHolder, position: Int) {
        holder.bind(spells[position])
    }

    override fun getItemCount(): Int = spells.size

    fun updateSpells(newSpells: List<Spell>) {
        spells = newSpells
        notifyDataSetChanged()
    }
}
