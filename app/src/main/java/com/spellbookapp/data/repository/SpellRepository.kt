package com.spellbookapp.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.spellbookapp.data.database.SpellDao
import com.spellbookapp.data.models.Spell
import com.spellbookapp.data.models.SpellEntity
import com.spellbookapp.network.DndApiService

// Repository for managing operations between API and database
class SpellRepository(
    private val api: DndApiService,
    private val dao: SpellDao
) {

    // Get all spells from the API
    suspend fun getAllSpellsFromApi(): List<Spell> {
        return try {
            // Store the API response
            val response = api.getAllSpells()
            if (response.isSuccessful) {
                response.body()?.results?.filterNotNull() ?: emptyList()
            } else {
                // Handle API errors
                Log.e("SpellRepository", "API call failed: ${response.code()} ${response.message()}")
                emptyList()
            }
        } catch (e: Exception) {
            // Handle Repository errors
            Log.e("SpellRepository", "Network error", e)
            emptyList()
        }
    }

    // Get the detailed spell data from the API by its index
    suspend fun getSpellDetails(index: String): Spell? {
        return api.getSpellByIndex(index).body()
    }

    // Store a spell as prepared in the database
    suspend fun prepareSpell(spell: SpellEntity) {
        dao.insertSpell(spell)
    }

    // Observe all the prepared spells in the database
    fun getPreparedSpells(): LiveData<List<SpellEntity>> {
        return dao.getAllSpells()
    }

    // Remove a prepared spell from prepared by its ID
    suspend fun removePreparedSpell(spellId: String) {
        dao.deleteSpellById(spellId)
    }
}

