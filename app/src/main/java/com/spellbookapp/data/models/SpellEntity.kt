package com.spellbookapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

// Entity for a spell saved in the database
@Entity(tableName = "spells")
data class SpellEntity(
    // Unique ID from the D&D API
    @PrimaryKey val spellId: String,
    val name: String,
    val level: Int,
    val school: String,
    val castingTime: String,
    val range: String,
    val duration: String,
    val components: String,
    val material: String?,
    val description: String,
    val higherLevel: String?,
    // Not from API, saves whether the spell is prepared or not
    val isPrepared: Boolean = false
)

// Converts a SpellEntity back to a spell in the app
fun SpellEntity.toSpell(): Spell {
    return Spell(
        index = this.spellId,
        name = this.name,
        level = this.level,
        // Convert the school name back into a ApiReference
        school = ApiReference(
            name = this.school,
            // index and url can be filled if needed
            index = "",
            url = ""
        ),
        casting_time = this.castingTime,
        range = this.range,
        duration = this.duration,
        components = this.components.split(", ").map { it.trim() },
        material = this.material,
        description = this.description.split("\n"),
        higher_level = this.higherLevel?.split("\n"),
        classes = emptyList(),
        isPrepared = this.isPrepared
    )
}
