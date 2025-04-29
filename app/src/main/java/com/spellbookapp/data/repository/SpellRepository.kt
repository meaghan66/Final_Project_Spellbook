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

    // technically working version with classes
    // but takes a REALLYYYY long time
//    suspend fun getAllSpellsFromApi(): List<Spell> {
//        return try {
//            val response = api.getAllSpells()
//            if (response.isSuccessful) {
//                val results = response.body()?.results?.filterNotNull() ?: emptyList()
//
//                // Fetch full details for each spell in parallel
//                results.mapNotNull { summary ->
//                    try {
//                        val detailResponse = api.getSpellByIndex(summary.index)
//                        if (detailResponse.isSuccessful) {
//                            detailResponse.body()
//                        } else {
//                            Log.e("SpellRepository", "Failed to fetch details for ${summary.index}")
//                            null
//                        }
//                    } catch (e: Exception) {
//                        Log.e("SpellRepository", "Error fetching spell details for ${summary.index}", e)
//                        null
//                    }
//                }
//            } else {
//                Log.e("SpellRepository", "API call failed: ${response.code()} ${response.message()}")
//                emptyList()
//            }
//        } catch (e: Exception) {
//            Log.e("SpellRepository", "Network error", e)
//            emptyList()
//        }
//    }

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

