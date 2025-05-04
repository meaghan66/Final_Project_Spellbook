package com.spellbookapp.data.database

import androidx.lifecycle.LiveData
import androidx.room.*

import com.spellbookapp.data.models.SpellEntity

// Data Access Object for accessing spell data in the room database
@Dao
interface SpellDao {

    // Insert a new spell or replace if it exists
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSpell(spell: SpellEntity)

    // Delete a spell
    @Delete
    suspend fun deleteSpell(spell: SpellEntity)

    // Get all spells as LiveData to observe in UI
    @Query("SELECT * FROM spells")
    fun getAllSpells(): LiveData<List<SpellEntity>>

    // Get a spell by its ID
    @Query("SELECT * FROM spells WHERE spellId = :spellId")
    suspend fun getSpellById(spellId: String): SpellEntity?

    // Delete a spell by its ID
    @Query("DELETE FROM spells WHERE spellId = :spellId")
    suspend fun deleteSpellById(spellId: String)
}
