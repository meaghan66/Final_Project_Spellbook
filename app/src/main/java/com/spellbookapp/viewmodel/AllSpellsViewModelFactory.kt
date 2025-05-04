package com.spellbookapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.spellbookapp.data.repository.SpellRepository

// ViewModel Factory for AllSpellsViewModel instances
class AllSpellsViewModelFactory(
    private val repository: SpellRepository
) : ViewModelProvider.Factory {
    // Create the ViewModel class
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AllSpellsViewModel::class.java)) {
            return AllSpellsViewModel(repository) as T
        }
        // Handle unknown ViewModel classes
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
