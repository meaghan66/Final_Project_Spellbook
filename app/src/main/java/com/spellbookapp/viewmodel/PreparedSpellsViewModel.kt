package com.spellbookapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.spellbookapp.data.models.SpellEntity
import com.spellbookapp.data.repository.SpellRepository

class PreparedSpellsViewModel(private val repository: SpellRepository) : ViewModel() {

    val preparedSpells: LiveData<List<SpellEntity>> = repository.getPreparedSpells()

}
