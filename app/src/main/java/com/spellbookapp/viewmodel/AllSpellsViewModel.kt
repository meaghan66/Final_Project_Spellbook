package com.spellbookapp.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.spellbookapp.data.models.Spell
import com.spellbookapp.data.repository.SpellRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AllSpellsViewModel(private val repository: SpellRepository) : ViewModel() {

    private val _spells = MutableLiveData<List<Spell>>()
    val spells: LiveData<List<Spell>> = _spells

    fun fetchSpells() {
        viewModelScope.launch {
            try {
                val spellsList = withContext(Dispatchers.IO) {
                    repository.getAllSpellsFromApi()
                }
                _spells.value = spellsList
            } catch (e: Exception) {
                Log.e("AllSpellsViewModel", "Error fetching spells", e)
                _spells.value = emptyList() // fallback to empty if API fails
            }
        }
    }

}
