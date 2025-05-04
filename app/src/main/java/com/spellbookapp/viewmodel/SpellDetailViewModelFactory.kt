package com.spellbookapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.spellbookapp.data.repository.SpellRepository

// Factory for the SpellDetailViewModel
class SpellDetailViewModelFactory(
    private val repository: SpellRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Create a ViewModel class
        if (modelClass.isAssignableFrom(SpellDetailViewModel::class.java)) {
            return SpellDetailViewModel(repository) as T
        }
        // Handle unknown ViewModel classes
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
