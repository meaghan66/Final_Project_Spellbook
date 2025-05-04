package com.spellbookapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.spellbookapp.data.models.toSpellEntity
import com.spellbookapp.data.repository.SpellRepository
import kotlinx.coroutines.launch

// ViewModel for managing the spell details
class SpellDetailViewModel(private val repository: SpellRepository) : ViewModel() {

    // Get a spell's details from the repository by ID
    fun getSpell(spellId: String) = liveData {
        val spell = repository.getSpellDetails(spellId)
        emit(spell)
    }

    // Prepare a spell in the database and mark as prepared
    fun prepareSpell(spell: com.spellbookapp.data.models.Spell) {
        viewModelScope.launch {
            repository.prepareSpell(spell.toSpellEntity())
        }
    }

    // Remove a spell from the prepared spells in the database
    fun removeSpell(spellId: String) {
        viewModelScope.launch {
            repository.removePreparedSpell(spellId)
        }
    }
}
