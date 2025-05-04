package com.spellbookapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.spellbookapp.data.models.SpellEntity
import com.spellbookapp.data.repository.SpellRepository

// ViewModel for prepared spells
class PreparedSpellsViewModel(private val repository: SpellRepository) : ViewModel() {

    // A LiveData list of all the prepared spells from the database
    val preparedSpells: LiveData<List<SpellEntity>> = repository.getPreparedSpells()

}
