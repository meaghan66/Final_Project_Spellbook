package com.spellbookapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "spells")
data class SpellEntity(
    @PrimaryKey val spellId: String, // unique ID from the DnD API
    val name: String,
    val level: Int,
    val school: String,
    val castingTime: String,
    val range: String,
    val duration: String,
    val components: String, // serialized for simplicity
    val material: String?,
    val description: String,
    val higherLevel: String?, // optional extra description
    val isFavorite: Boolean = false
)

fun SpellEntity.toSpell(): Spell {
    return Spell(
        index = this.spellId,
        name = this.name,
        level = this.level,
        school = ApiReference( // Convert the stored school name back into a minimal ApiReference
            name = this.school,
            index = "", // Optional: fill if needed
            url = ""    // Optional: fill if needed
        ),
        casting_time = this.castingTime,
        range = this.range,
        duration = this.duration,
        components = this.components.split(", ").map { it.trim() },
        material = this.material,
        description = this.description.split("\n"),
        higher_level = this.higherLevel?.split("\n"),
        classes = emptyList(), // Not stored locally, so leave empty or fetch separately
        isFavorite = this.isFavorite
    )
}
