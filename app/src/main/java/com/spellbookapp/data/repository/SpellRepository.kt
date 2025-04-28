package com.spellbookapp.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.spellbookapp.data.database.SpellDao
import com.spellbookapp.data.models.Spell
import com.spellbookapp.data.models.SpellEntity
import com.spellbookapp.network.DndApiService

class SpellRepository(
    private val api: DndApiService,
    private val dao: SpellDao
) {

    suspend fun getAllSpellsFromApi(): List<Spell> {
        return try {
            val response = api.getAllSpells()
            if (response.isSuccessful) {
                // Change from `spells` to `results`
                response.body()?.results?.filterNotNull() ?: emptyList()
            } else {
                Log.e("SpellRepository", "API call failed: ${response.code()} ${response.message()}")
                emptyList()
            }
        } catch (e: Exception) {
            Log.e("SpellRepository", "Network error", e)
            emptyList()
        }
    }

    suspend fun getSpellDetails(index: String): Spell? {
        return api.getSpellByIndex(index).body()
    }

    suspend fun prepareSpell(spell: SpellEntity) {
        dao.insertSpell(spell)
    }

    fun getPreparedSpells(): LiveData<List<SpellEntity>> {
        return dao.getAllSpells()
    }

    suspend fun removePreparedSpell(spellId: String) {
        dao.deleteSpellById(spellId)
    }
}

