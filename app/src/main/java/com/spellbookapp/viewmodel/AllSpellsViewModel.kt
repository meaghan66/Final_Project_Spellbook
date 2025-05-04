package com.spellbookapp.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.spellbookapp.data.models.Spell
import com.spellbookapp.data.repository.SpellRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// ViewModel for fetching a list of every spell from the API
class AllSpellsViewModel(private val repository: SpellRepository) : ViewModel() {

    // Hold the spell data as LiveData
    private val _spells = MutableLiveData<List<Spell>>()
    val spells: LiveData<List<Spell>> = _spells

    // Fetch spells from the repository
    fun fetchSpells() {
        viewModelScope.launch {
            try {
                // API call on the background thread
                val spellsList = withContext(Dispatchers.IO) {
                    repository.getAllSpellsFromApi()
                }
                _spells.value = spellsList
            } catch (e: Exception) {
                // Handle errors for fetching the spells
                Log.e("AllSpellsViewModel", "Error fetching spells", e)
                // Empty list if API fails
                _spells.value = emptyList()
            }
        }
    }

}
