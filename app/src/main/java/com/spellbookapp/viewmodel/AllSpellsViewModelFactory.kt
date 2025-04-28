package com.spellbookapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.spellbookapp.data.repository.SpellRepository

class AllSpellsViewModelFactory(
    private val repository: SpellRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AllSpellsViewModel::class.java)) {
            return AllSpellsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
