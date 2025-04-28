package com.spellbookapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.spellbookapp.data.models.toSpellEntity
import com.spellbookapp.data.repository.SpellRepository
import kotlinx.coroutines.launch

class SpellDetailViewModel(private val repository: SpellRepository) : ViewModel() {

    fun getSpell(spellId: String) = liveData {
        val spell = repository.getSpellDetails(spellId)
        emit(spell)
    }

    fun prepareSpell(spell: com.spellbookapp.data.models.Spell) {
        viewModelScope.launch {
            repository.prepareSpell(spell.toSpellEntity())
        }
    }

    fun removeSpell(spellId: String) {
        viewModelScope.launch {
            repository.removePreparedSpell(spellId)
        }
    }
}
