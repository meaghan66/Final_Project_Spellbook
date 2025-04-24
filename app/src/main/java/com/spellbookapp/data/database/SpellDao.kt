package com.spellbookapp.data.database

import androidx.lifecycle.LiveData
import androidx.room.*

import com.spellbookapp.data.models.SpellEntity

@Dao
interface SpellDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSpell(spell: SpellEntity)

    @Delete
    suspend fun deleteSpell(spell: SpellEntity)

    @Query("SELECT * FROM spells")
    fun getAllSpells(): LiveData<List<SpellEntity>>

    @Query("SELECT * FROM spells WHERE spellId = :spellId")
    suspend fun getSpellById(spellId: String): SpellEntity?

    @Query("DELETE FROM spells WHERE spellId = :spellId")
    suspend fun deleteSpellById(spellId: String)
}
