package com.spellbookapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.spellbookapp.data.repository.SpellRepository

// Factory for creating the PreparedSpellsViewModel instances
class PreparedSpellsViewModelFactory(
    private val repository: SpellRepository
) : ViewModelProvider.Factory {
    // Create the ViewModel class
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PreparedSpellsViewModel::class.java)) {
            return PreparedSpellsViewModel(repository) as T
        }
        // Handle unknown ViewModel classes
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
